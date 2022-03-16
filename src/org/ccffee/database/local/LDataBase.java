package org.ccffee.database.local;

import org.ccffee.database.DBHandler;
import org.ccffee.database.DBResult;
import org.ccffee.database.comparison.Comparator;
import org.ccffee.database.exception.InvalidColumnException;
import org.ccffee.database.exception.InvalidRecordException;
import org.ccffee.database.exception.InvalidTableException;
import org.ccffee.database.statements.*;
import org.ccffee.utils.io.*;
import org.ccffee.utils.Final;
import org.ccffee.utils.FinalInt;
import org.ccffee.utils.exceptions.ExceptionUtils;
import org.ccffee.utils.iteration.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 *
 * Class LDataBase is a build in database.
 *
 */
public class LDataBase implements Savable, DBHandler {
	private File dataFolder;
	private ArrayList<DataBaseEntry> entrys = new ArrayList<>();

	/**
	 *
	 * Creates a LDataBase with a specific data folder
	 *
	 * @param dataFolder
	 */
	public LDataBase(File dataFolder) {
		this.dataFolder = dataFolder;
	}
	/**
	 *
	 * Creates a LDataBase
	 *
	 */
	public LDataBase() {
		this(new File("data"));
	}

	public boolean isRegistered(String entry) {
		for(DataBaseEntry t : entrys)if(t.getName().equalsIgnoreCase(entry))return true;
		return new File(dataFolder,entry+".dat").exists();
	}
	void createTable(String table, String[] columns) throws InvalidTableException {
		if(isRegistered(table)) throw new InvalidTableException("Table already exist.");
		DataBaseEntry dbe;
		entrys.add((dbe = new DataBaseEntry(table.toLowerCase())));
		byte[][][] data = new byte[1][columns.length][];
		for(int i = 0;i<columns.length;i++)data[0][i]=ByteUtils.stringToBytes(columns[i]);
		dbe.setData(data);
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

	/**
	 *
	 * Saves all date in this LDataBase.
	 *
	 */
	public void save() {
		for(DataBaseEntry e : entrys)save(e);
	}
	private void save(DataBaseEntry dbe) {
		try {
			DataLoader.save(dbe.getData(), FileUtils.createNewFileRecursive(dbe.getFile(dataFolder)));
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
			Table[] table = new Table[]{ExceptionUtils.getIE(() -> getTable(select.next().getTable()))};
			if (table[0] == null) throw new InvalidTableException(select.next().getTable());
			Final<JoinableStatement<DBResult>> joinable = new Final<>(select.next());
			Final<WhereStatement<DBResult>> where = new Final<>();
			OrderByStatement<DBResult> orderBy = null;
			JoinStatement[] joins = new JoinStatement[0];
			do{
				if(joinable.get().next() instanceof JoinStatement){
					joinable.set((JoinableStatement<DBResult>) joinable.get().next());
					Table t = ExceptionUtils.getIE(()->getTable(joinable.get().getTable()));
					if(t==null)throw new InvalidTableException(joinable.get().getTable());
					table=ArrayUtils.addAndExpand(table,t);
					joins=ArrayUtils.addAndExpand(joins,(JoinStatement)joinable.get());
				}else if(joinable.get().next() instanceof OrderByStatement){
					orderBy = (OrderByStatement<DBResult>) joinable.get().next();
					break;
				} else{
					where.set((WhereStatement<DBResult>) joinable.get().next());
					break;
				}
			}while (true);
			int[][] columns = getColumns(select.getColumns(), table);
			ArrayList<Condition>[] conditions = buildConditions(where.get(),table);
			for(int i = 0; i < table.length;i++){
				SimpleTable st = new SimpleTable(table[i].getName(),new byte[][][]{table[i].getData()[0]});
				forRow(conditions[i], table[i].getData(),row->st.addLine(row));
				table[i]=st;
			}
			table = join(table,joins);
			while (orderBy==null&&!where.isEmpty()){
				AfterWhereEndStatement s = ExceptionUtils.getIE(()-> where.get().next().next());
				if(s instanceof OrderByStatement)orderBy = (OrderByStatement)s;
				else where.set((WhereStatement<DBResult>) s);
			}
			if(orderBy!=null){
				Comparator comp = orderBy.getComparator();
				boolean asc = orderBy.isAscending();
				int[] c = getColumn(orderBy.getColumn(),table);
				byte[][] b;
				for(int i = 1;i+1<table[0].getData().length;i++){
					int x = i;
					for(int j = i+1;j<table[0].getData().length;j++)
						if (asc && comp.less(table[c[0]].getData()[j][c[1]], table[c[0]].getData()[x][c[1]]) ||
								!asc && comp.greater(table[c[0]].getData()[j][c[1]], table[c[0]].getData()[x][c[1]]))x=j;
					for (Table t : table) {
						b = t.getData()[x];
						t.getData()[x] = t.getData()[i];
						t.getData()[i] = b;
					}
				}
			}
			Data[][] result = new Data[table[0].getData().length-1][columns.length];
			for(int i = 0;i<result.length;i++)
				for(int j = 0;j<columns.length;j++)
					result[i][j] = new Data(table[columns[j][0]].getData()[i+1][columns[j][1]]);
			return CompletableFuture.completedFuture(new DBResult(select.getColumns(), result));
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
			Table table = ExceptionUtils.getIE(() -> getTable(update.getTable()));
			if (table == null) throw new InvalidTableException();
			int[] columns = getColumns(update.getColumns(), table.getData()[0]);
			ByteConvertable[] value = update.next().getData();
			if(value.length!=columns.length)throw new InvalidRecordException();
			forRow(buildConditions(update.next().next(),new Table[]{table})[0], table.getData(),row->{
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
			ArrayList<Condition>[] conditions = buildConditions(delete.next().next(),new Table[]{entry});
			boolean is;
			int i = 1;
			while ( i < table.length) {
				is = true;
				for (Condition c : conditions[0]){
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
		int i = s.indexOf('.');
		byte[] b1 = ByteUtils.stringToBytes(i>-1&&++i<s.length()?s.substring(i):s);
		for( i = 0; i<b.length;i++)if(Arrays.equals(b[i],b1))return i;
		return -1;
	}
	private static int[] getColumn(String s, Table... tables) throws InvalidColumnException {
		int[] out = null;
		int x = s.indexOf('.');
		if(x>-1){
			String table = s.substring(0,x);
			for(int j = 0; j < tables.length; j++)if(tables[j].getName().equals(table)){
				out = new int[]{j,getColumn(s,tables[j].getData()[0])};
				break;
			}
		}else{
			int c;
			for(FinalInt j = new FinalInt(0); j.get() < tables.length;j.inc())if((c=ExceptionUtils.getIE(()->getColumn(s,tables[j.get()].getData()[0]),-1))!=-1){
				out = new int[]{j.get(),c};
				break;
			}
		}
		if(out==null)throw new InvalidColumnException(s);
		return out;
	}
	private static int[] getColumns(CharSequence[] s, byte[][] b)throws InvalidColumnException {
		int[] result = new int[s.length];
		for(int i = 0;i<s.length;i++) {
			int c = getColumn(s[i].toString(),b);
			if(c==-1)throw new InvalidColumnException(s[i].toString());
				result[i]=c;
		}
		return result;
	}
	private static int[][] getColumns(String[] s, Table[] tables)throws InvalidColumnException{
		int[][] out = new int[s.length][];
		for(int i = 0;i<s.length;i++)
			out[i]=getColumn(s[i],tables);
		return out;
	}
	private static ArrayList<Condition>[] buildConditions(WhereStatement where, Table[] table)throws InvalidColumnException{
		ArrayList<Condition>[] conditions = new ArrayList[table.length];
		for (int i = 0; i<conditions.length;i++) conditions[i] = new ArrayList<>();
		int column[];

		while (where != null) {
			column = getColumn(where.getColumn(),table);
			switch (where.getMethod()) {
				case EQUAL:
					conditions[column[0]].add(new EqualCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0]));
					break;
				case LESS:
					conditions[column[0]].add(new LessCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0], where.getComparator()));
					break;
				case GREATER:
					conditions[column[0]].add(new GreaterCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0], where.getComparator()));
					break;
				case LESS_OR_EQUAL:
					conditions[column[0]].add(new LessEqualCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0], where.getComparator()));
					break;
				case GREATER_OR_EQUAL:
					conditions[column[0]].add(new GreaterEqualCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0], where.getComparator()));
					break;
				case NOT_EQUAL:
					conditions[column[0]].add(new NotEqualCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0]));
					break;
				case BETWEEN:
					conditions[column[0]].add(new BetweenCondition(where.isAnd(), where.isNot(), column[1], where.getData()[0], where.getData()[1],where.getComparator()));
					break;
				case IN:
					conditions[column[0]].add(new InCondition(where.isAnd(), where.isNot(), column[1], where.getData()));
					break;
			}
			WhereStatement w = where;
			where = ExceptionUtils.getIE(() -> (WhereStatement)w.next().next());
		}
		return conditions;
	}

	private static Table[] join(Table[] table, JoinStatement[] join) throws InvalidColumnException {
		for(int i = 0;i<join.length;i++){
			int[] c1 = getColumn(join[i].getColumn1(),table);
			int[] c2 = getColumn(join[i].getColumn2(),table);
			if(c1[0]==c2[0])throw new InvalidColumnException(join[i].getColumn1()+" and "+join[i].getColumn2()+" can not be the same.");
			if(c1[0]==i+1){
				int[] c = c1;
				c1=c2;
				c2=c;
			}else if(c2[0]!=i+1) throw new InvalidColumnException(join[i-1].getColumn1()+" or "+join[i-1].getColumn2()+" hast to be from joined table");
			Table[] t = Arrays.copyOf(table,i+2);
			for(int j = 0;j<t.length;j++)
				table[j]=new SimpleTable(table[j].getName(),new byte[][][]{table[j].getData()[0]});
			byte[][][] b1 = t[c1[0]].getData();
			byte[][][] b2 = t[c2[0]].getData();
			boolean add;
			boolean[] add2;
			switch(join[i].getType()){
				case INNER:
					for(int l1 = 1; l1< b1.length;l1++){
						System.out.println(l1);
						for(int l2 = 1; l2<b2.length;l2++)
							if(Arrays.equals(b1[l1][c1[1]],b2[l2][c2[1]])) {
								System.out.println("eq"+l2);
								for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
								((SimpleTable) table[c2[0]]).addLine(b2[l2]);
							}
					}
					break;
				case LEFT:
					for(int l1 = 1; l1< b1.length;l1++){
						add = false;
						for(int l2 = 1; l2<b2.length;l2++)
							if(Arrays.equals(b1[l1][c1[1]],b2[l2][c2[1]])) {
								add = true;
								for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
								((SimpleTable) table[c2[0]]).addLine(b2[l2]);
							}
						if(!add){
							for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
							((SimpleTable) table[c2[0]]).addLine(new byte[t[c2[0]].getData()[0].length][0]);
						}
					}
					break;
				case RIGHT:
					add2 = new boolean[t[c2[0]].getData().length];
					for(int l1 = 1; l1< b1.length;l1++)
						for(int l2 = 1; l2<b2.length;l2++)
							if(Arrays.equals(b1[l1][c1[1]],b2[l2][c2[1]])) {
								add2[l2]=true;
								for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
								((SimpleTable) table[c2[0]]).addLine(b2[l2]);
							}
					for(int l2 = 1;l2 < add2.length;l2++)if(!add2[l2]){
						for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(new byte[t[j].getData()[0].length][0]);
						((SimpleTable) table[c2[0]]).addLine(b2[l2]);
					}
					break;
				case FULL:
					add2 = new boolean[t[c2[0]].getData().length];
					for(int l1 = 1; l1< b1.length;l1++){
						add = false;
						for(int l2 = 1; l2<b2.length;l2++)
							if(Arrays.equals(b1[l1][c1[1]],b2[l2][c2[1]])) {
								add = true;
								add2[l2]=true;
								for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
								((SimpleTable) table[c2[0]]).addLine(b2[l2]);
							}
						if(!add){
							for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(t[j].getData()[l1]);
							((SimpleTable) table[c2[0]]).addLine(new byte[t[c2[0]].getData()[0].length][0]);
						}
					}
					for(int l2 = 1;l2 < add2.length;l2++)if(!add2[l2]){
						for (int j = 0; j <= i; j++) ((SimpleTable) table[j]).addLine(new byte[t[j].getData()[0].length][0]);
						((SimpleTable) table[c2[0]]).addLine(b2[l2]);
					}
			}
		}
		return table;
	}

	private static void forRow(ArrayList<Condition> conditions, byte[][][] table, Consumer<byte[][]> forRow)throws InvalidColumnException{
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
