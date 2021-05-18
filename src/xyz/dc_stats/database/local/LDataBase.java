package xyz.dc_stats.database.local;

import xyz.dc_stats.database.*;
import xyz.dc_stats.database.exception.InvalidRecordException;
import xyz.dc_stats.database.exception.InvalidColumnException;
import xyz.dc_stats.database.exception.InvalidTableException;
import xyz.dc_stats.database.statements.*;
import xyz.dc_stats.utils.Final;
import xyz.dc_stats.utils.exceptions.ExceptionUtils;
import xyz.dc_stats.utils.io.*;
import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
		return new File(dataFolder,entry+".dat").exists();
	}
	void createTable(String table,String[] columns) throws InvalidTableException {
		if(isRegistered(table)) throw new InvalidTableException("Table already exist.");
		DataBaseEntry dbe;
		entrys.add((dbe =new DataBaseEntry(table.toLowerCase())));
		byte[][][] data = new byte[1][columns.length][];
		for(int i = 0;i<columns.length;i++)data[0][i]=ByteUtils.stringToBytes(columns[i]);
		dbe.setData(data);
		save(dbe);
	}
	DataBaseEntry getTable(String table) {
		for(DataBaseEntry e : entrys)if(e.getName().equalsIgnoreCase(table))return e;
		if(new File(dataFolder,table+".dat").exists()){
			DataBaseEntry e = new DataBaseEntry(table);
			entrys.add(e);
			load(e);
			return e;
		}
		return null;
	}
	public void save() {
		for(DataBaseEntry e : entrys)save(e);
	}
	private void save(DataBaseEntry dbe) {
		try {
			DataLoader.save(dbe.getData(),FileUtils.createNewFileRecursive(dbe.getFile(dataFolder)));
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
		byte[][][] data = new byte[0][0][];
		try {
			data=DataLoader.load(dbe.getFile(dataFolder));
		} catch (FileNotFoundException e) {
			dbe.setData(new byte[0][0][]);
		} catch (IOException e) {
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
	public InsertStatement insert() {
		return new InsertStatement(this);
	}

	@Override
	public UpdateStatement update(String table, String... columns) {
		return new UpdateStatement(this,table,columns);
	}

	@Override
	public DeleteStatement delete() {
		return new DeleteStatement(this);
	}

	@Override
	public CompletableFuture<DBResult> process(SelectStatement select){
		try {
			byte[][][] table = ExceptionUtils.getIE(() -> getTable(select.next().getTable()).getData());
			if (table == null) throw new InvalidTableException();
			int[] columns = getColumns(select.getColumns(), table[0]);
			Final<Data[][]> result = new Final<>(new Data[0][]);
			forRow(select.next().next(), table,row->{
				Data[] r = new Data[columns.length];
				for (int z = 0; z < columns.length; z++) r[z] = new Data(row[columns[z]]);
				result.set(ArrayUtils.addAndExpand(result.get(), r));
			});
			return CompletableFuture.completedFuture(new DBResult(select.getColumns(), result.get()));
		}catch (InvalidColumnException | InvalidTableException e){
			return CompletableFuture.failedFuture(e);
		}
	}

	@Override
	public CompletableFuture<Void> process(CreateTableStatement createTable) {
		try{
			createTable(createTable.getName(), createTable.getColumns());
		}catch (Exception e){
			return CompletableFuture.failedFuture(e);
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> process(InsertStatement insert) {
			try {
				DataBaseEntry entry = getTable(insert.next().getTable());
				if(entry == null)throw new InvalidTableException();
				int[] columns = getColumns(insert.next().getColumns(), entry.getData()[0]);
				ByteConvertable[][] values = insert.next().next().getValues();
				for(ByteConvertable[] v : values)if(v.length!=columns.length)throw new InvalidRecordException();
				byte[][][] table = new byte[values.length][entry.getData()[0].length][0];

				for(int i = 0;i<values.length;i++) for(int j = 0;j<columns.length;j++)
					table[i][columns[j]]=values[i][j].toByteArray();
				entry.setData(ArrayUtils.addArrayAndExpand(entry.getData(),table));
				return CompletableFuture.completedFuture(null);
			}catch (InvalidTableException | InvalidColumnException |InvalidRecordException e){
				return CompletableFuture.failedFuture(e);
			}
	}

	@Override
	public CompletableFuture<Void> process(UpdateStatement update) {
		try {
			byte[][][] table = ExceptionUtils.getIE(() -> getTable(update.getTable()).getData());
			if (table == null) throw new InvalidTableException();
			int[] columns = getColumns(update.getColumns(), table[0]);
			ByteConvertable[] value = update.next().getData();
			if(value.length!=columns.length)throw new InvalidRecordException();
			forRow(update.next().next(), table,row->{
				for (int i = 0;i<columns.length;i++)row[columns[i]]=value[i].toByteArray();
			});
			return CompletableFuture.completedFuture(null);
		}catch (InvalidColumnException | InvalidTableException | InvalidRecordException e){
			return CompletableFuture.failedFuture(e);
		}
	}

	@Override
	public CompletableFuture<Void> process(DeleteStatement delete) {
		try {
			DataBaseEntry entry = getTable(delete.next().getTable());
			if (entry == null) throw new InvalidTableException();
			byte[][][] table = entry.getData();
			ArrayList<Condition> conditions = buildConditions(delete.next().next(),table[0]);
			boolean is;
			int i = 1;
			while ( i < table.length) {
				is = true;
				for (Condition c : conditions){
					is = c.is(is, table[i]);
				}
				if (is)table = ArrayUtils.removeAndShrink(table,i);
				else i++;
			}
			entry.setData(table);
			return CompletableFuture.completedFuture(null);
		}catch (InvalidColumnException | InvalidTableException e){
			return CompletableFuture.failedFuture(e);
		}
	}

	private static int getColumn(String s,byte[][] b){
		byte[] b1 = ByteUtils.stringToBytes(s);
		for(int i = 0; i<b.length;i++)if(Arrays.equals(b[i],b1))return i;
		return -1;
	}
	private static int[] getColumns(String[] s, byte[][] b)throws InvalidColumnException {
		int[] result = new int[s.length];
		for(int i = 0;i<s.length;i++) {
			int c = getColumn(s[i],b);
			if(c==-1)throw new InvalidColumnException(s[i]);
				result[i]=c;
		}
		return result;
	}
	private static ArrayList<Condition> buildConditions(WhereStatement where, byte[][] columns)throws InvalidColumnException{
		ArrayList<Condition> conditions = new ArrayList<>();
		int column;
		while (where != null) {
			column = getColumn(where.getColumn(), columns);
			if (column == -1) throw new InvalidColumnException(where.getColumn());
			switch (where.getMethod()) {
				case EQUAL:
					conditions.add(new EqualCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case LESS:
					conditions.add(new LessCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case GREATER:
					conditions.add(new GreaterCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case LESS_OR_EQUAL:
					conditions.add(new LessEqualCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case GREATER_OR_EQUAL:
					conditions.add(new GreaterEqualCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case NOT_EQUAL:
					conditions.add(new NotEqualCondition(where.isAnd(), where.isNot(), column, where.getData()[0]));
					break;
				case BETWEEN:
					conditions.add(new BetweenCondition(where.isAnd(), where.isNot(), column, where.getData()[0], where.getData()[1]));
					break;
				case IN:
					conditions.add(new InCondition(where.isAnd(), where.isNot(), column, where.getData()));
					break;
			}
			final WhereStatement w = where;
			where = ExceptionUtils.getIE(() -> w.next().next());
		}
		return conditions;
	}
	private static void forRow(WhereStatement where, byte[][][] table, Consumer<byte[][]> forRow)throws InvalidColumnException{
		ArrayList<Condition> conditions = buildConditions(where,table[0]);
		boolean is;
		for (int i = 1; i < table.length; i++) {
			is = true;
			for (Condition c : conditions){
				is = c.is(is, table[i]);
			}
			if (is) forRow.accept(table[i]);
		}
	}
}
