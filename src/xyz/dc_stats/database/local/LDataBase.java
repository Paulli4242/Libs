package xyz.dc_stats.database.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import xyz.dc_stats.database.DataBaseEntry;
import xyz.dc_stats.database.IDataBase;
import xyz.dc_stats.database.statements.SelectStatement;
import xyz.dc_stats.utils.io.Savable;
import xyz.dc_stats.utils.io.FileFormatException;

public class LDataBase implements IDataBase, Savable {
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
	public boolean registerTable(String table,String filePath) {
		if(!isRegistered(table)) {
			DataBaseEntry dbe;
			entrys.add((dbe =new DataBaseEntry(table.toLowerCase(), filePath)));
			load(dbe);
			return true;
		}else return false;
	}
	public boolean registerTable(String table) {
		return registerTable(table, table+".dat");
	}
	public boolean unregisterTable(String table){
		return entrys.removeIf(new Predicate<DataBaseEntry>() {

			@Override
			public boolean test(DataBaseEntry t) {
				return t.getName().equalsIgnoreCase(table);
			}
		});
	}
	public void forceRegisterTable(String table,String filePath) {
		unregisterTable(table);
		registerTable(table, filePath);
	}
	public void forceRegisterTable(String table) {
		unregisterTable(table);
		registerTable(table);
	}
	private DataBaseEntry getTable(String table) {
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

	public SelectStatement select() {
		return null;
	}
}
