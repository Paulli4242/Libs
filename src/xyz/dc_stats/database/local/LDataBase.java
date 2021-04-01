package xyz.dc_stats.database.local;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.database.DBResult;
import xyz.dc_stats.database.Data;
import xyz.dc_stats.database.statements.CreateStatement;
import xyz.dc_stats.database.statements.CreateTableStatement;
import xyz.dc_stats.database.statements.SWhereStatement;
import xyz.dc_stats.database.statements.SelectStatement;
import xyz.dc_stats.utils.Null;
import xyz.dc_stats.utils.exceptions.ExceptionUtils;
import xyz.dc_stats.utils.io.ByteUtils;
import xyz.dc_stats.utils.io.FileFormatException;
import xyz.dc_stats.utils.io.Savable;
import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class LDataBase implements Savable, DBHandler {
	private File dataFolder;
	private ArrayList<DataBaseEntry> entrys = new ArrayList<>();
	public LDataBase(File dataFolder) {
		this.dataFolder = dataFolder;
	}
	public LDataBase() {
		this(new File("data"));
	}
	public boolean isRegistered(String entry) {
		for(DataBaseEntry t : entrys)if(t.getName().equalsIgnoreCase(entry))return true;
		return false;
	}
	void createTable(String table,String[] columns) {
		if(isRegistered(table)) throw new IllegalArgumentException("Table is exists");
		DataBaseEntry dbe;
		entrys.add((dbe =new DataBaseEntry(table.toLowerCase())));
		byte[][][] data = new byte[1][columns.length][];
		for(int i = 0;i<columns.length;i++)data[0][i]=ByteUtils.stringToBytes(columns[i]);
		dbe.setData(data);
	}
	DataBaseEntry getTable(String table) {
		for(DataBaseEntry e :entrys)if(e.getName().equalsIgnoreCase(table))return e;
		return null;
	}
	public void save() {
		for(DataBaseEntry e : entrys)save(e);
	}
	private void save(DataBaseEntry dbe) {
		try {
			File f = dbe.getFile(dataFolder);
			if(!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			byte[][][] data = dbe.getData();
			FileOutputStream out = new FileOutputStream(f);
			for(int l = 0; l<data.length;l++) {
				out.write(251);
				for(int c = 0;c<data[l].length;c++) {
					if(data[l][c]!=null) {
						int n;
						for(n = 0; n<data[l][c].length; n++) {
							if(n%250==0)out.write((data[l][c].length-n)<250?data[l][c].length-n:250);
							out.write(data[l][c][n]);
						}
						if(data[l][c].length%250==0)out.write(0);
					}else out.write(0);
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void reload() {
		load();
	}
	private void load() {
		for (DataBaseEntry e : entrys)load(e);
	}
	private void load(DataBaseEntry dbe) {
		byte [][][] data = new byte[0][0][];
		try {
			FileInputStream in = new FileInputStream(dbe.getFile(dataFolder));
			int bte = in.read();
			int l=-1,c=-1,i=0;
			boolean next = false;
			while(bte != -1) {
				if(l==-1||c==-1 || i>=data[l][c].length) {
					if(bte == 251) {
						l++;
						c=-1;
						data = Arrays.copyOf(data, l+1);
						data[l] = new byte[0][];
					}
					else if(l==-1)throw new FileFormatException("missing next line byte");
					else if(c!=-1&&data[l][c].length!=0&&data[l][c].length%250==0&&!next) {
						if(bte!=0)data[l][c] = Arrays.copyOf(data[l][c], data[l][c].length+bte);
						else next = true;
					}
					else {
						next = false;
						c++;
						i=0;
						data[l] = Arrays.copyOf(data[l], c+1);
						data[l][c]= new byte[bte];
					}
				}else {
					data[l][c][i] = (byte) bte;
					i++;
				}
				bte = in.read();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
		dbe.setData(data);
	}
	@Override
	public SelectStatement select(String ... columns) {
		return new SelectStatement(this,columns);
	}

	@Override
	public CreateStatement create() {
		return new CreateStatement(this);
	}

	@Override
	public CompletableFuture<DBResult> process(SelectStatement select){
		byte[][][] table = ExceptionUtils.getIE(()->getTable(select.next().getTable()).getData());
		if(table == null)return CompletableFuture.failedFuture(new IllegalArgumentException("Unknown table"));

		SWhereStatement where = select.next().next();
		ArrayList<Condition> conditions = new ArrayList<>();
		int column;
		while(where!=null){
			column = getColumn(where.getColumn(), table[0]);
			if(column==-1)return CompletableFuture.failedFuture(new IllegalArgumentException("Unknown column: "+where.getColumn()));
			switch(where.getMethod()){
				case EQUAL:
					conditions.add(new EqualCondition(where.isAnd(), where.isNot(),column,where.getData()[0]));
					break;
				case LESS:
					conditions.add(new LessCondition(where.isAnd(), where.isNot(), column,where.getData()[0]));
					break;
				case GREATER:
					conditions.add(new GreaterCondition(where.isAnd(), where.isNot(), column,where.getData()[0]));
					break;
				case LESS_OR_EQUAL:
					conditions.add(new LessEqualCondition(where.isAnd(), where.isNot(), column,where.getData()[0]));
					break;
				case GREATER_OR_EQUAL:
					conditions.add(new GreaterEqualCondition(where.isAnd(), where.isNot(), column,where.getData()[0]));
					break;
				case NOT_EQUAL:
					conditions.add(new NotEqualCondition(where.isAnd(), where.isNot(), column,where.getData()[0]));
					break;
				case BETWEEN:
					conditions.add(new BetweenCondition(where.isAnd(), where.isNot(),column,where.getData()[0],where.getData()[1]));
					break;
				case IN:
					conditions.add(new InCondition(where.isAnd(), where.isNot(), column,where.getData()));
					break;
			}
			final SWhereStatement w = where;
			where = ExceptionUtils.getIE(()->w.next().next());
		}
		String[] strColumns = select.getColumns();
		int[] columns = new int[strColumns.length];
		int i = 0;
		while(i<strColumns.length) {
			int c = getColumn(strColumns[i],table[0]);
			if(c==-1)CompletableFuture.failedFuture(new IllegalArgumentException("Unknown column: "+strColumns[i]));
			else{
				columns[i]=c;
				i++;
			}
		}
		Data[][] result = new Data[0][];
		boolean is;
		for(i=1;i<table.length;i++){
			is=true;
			for(Condition c : conditions)is = c.is(is,table[i]);
			if(is){
				Data[] r = new Data[columns.length];
				for(int z = 0;z<columns.length;z++)r[z]=new Data(table[i][columns[z]]);
				result = ArrayUtils.addAndExpand(result,r);
			}
		}
		return CompletableFuture.completedFuture(new DBResult(strColumns,result));
	}

	@Override
	public CompletableFuture<Null> process(CreateTableStatement createTable) {
		try{
			createTable(createTable.getName(), createTable.getColumns());
		}catch (Exception e){
			return CompletableFuture.failedFuture(e);
		}
		return CompletableFuture.completedFuture(new Null());
	}
	public static int getColumn(String s,byte[][] b){
		byte[] b1 = ByteUtils.stringToBytes(s);
		for(int i = 0; i<b.length;i++)if(Arrays.equals(b[i],b1))return i;
		return -1;
	}
}
