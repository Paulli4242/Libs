package org.ccffee.utils.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
	private FileUtils(){

	}
	public static List<File> listAllFiles(File directory) {
		if(directory.isDirectory()) {
			ArrayList<File> files = new ArrayList<>();
			for(File f : directory.listFiles()) {
				if(f.isDirectory())files.addAll(listAllFiles(f));
				else files.add(f);
			}
			return files;
		}else return null;
	}
	public static List<File> listAllFiles(File directory, FileFilter filter) {
		if(directory.isDirectory()) {
			ArrayList<File> files = new ArrayList<>();
			for(File f : directory.listFiles()) {
				if(f.isDirectory())files.addAll(listAllFiles(f,filter));
				else if(filter.accept(f)) files.add(f);
			}
			return files;
		}else return null;
	}
	public static void deleteRecursive(File f){
		if(f.isDirectory())for(File f1 : f.listFiles())deleteRecursive(f1);
		f.delete();
	}
	public static List<File> listAllFiles(File directory, FilenameFilter filter) {
		if(directory.isDirectory()) {
			ArrayList<File> files = new ArrayList<>();
			for(File f : directory.listFiles()) {
				if(f.isDirectory())files.addAll(listAllFiles(f,filter));
				else if(filter.accept(f,f.getName())) files.add(f);
			}
			return files;
		}else return null;
	}
	
	public static File createNewFile(File file) throws IOException {
		file.createNewFile();
		return file;
	}
	public static File createNewFile(String pathname) throws IOException {
		return createNewFile(new File(pathname));
	}
	public static File createNewFile(URI uri) throws IOException {
		return createNewFile(new File(uri));
	}
	public static File createNewFile(File parent, String child) throws IOException {
		return createNewFile(new File(parent,child));
	}
	public static File createNewFile(String parent, String child) throws IOException {
		return createNewFile(new File(parent,child));
	}
	public static File mkdir(File file) throws IOException {
		file.mkdir();
		return file;
	}
	public static File mkdir(String pathname) throws IOException {
		return mkdir(new File(pathname));
	}
	public static File mkdir(URI uri) throws IOException {
		return mkdir(new File(uri));
	}
	public static File mkdir(File parent, String child) throws IOException {
		return mkdir(new File(parent,child));
	}
	public static File mkdir(String parent, String child) throws IOException {
		return mkdir(new File(parent,child));
	}
	public static File mkdirs(File file) throws IOException {
		file.mkdirs();
		return file;
	}
	public static File mkdirs(String pathname) throws IOException {
		return mkdirs(new File(pathname));
	}
	public static File mkdirs(URI uri) throws IOException {
		return mkdirs(new File(uri));
	}
	public static File mkdirs(File parent, String child) throws IOException {
		return mkdirs(new File(parent,child));
	}
	public static File mkdirs(String parent, String child) throws IOException {
		return mkdirs(new File(parent,child));
	}
	public static File createNewFileRecursive(File file) throws IOException {
		file.getParentFile().mkdirs();
		file.createNewFile();
		return file;
	}
	public static File createNewFileRecursive(URI uri) throws IOException {
		return createNewFileRecursive(new File(uri));
	}
	public static File createNewFileRecursive(File parent, String child) throws IOException {
		return createNewFileRecursive(new File(parent,child));
	}
	public static File createNewFileRecursive(String parent, String child) throws IOException {
		return createNewFileRecursive(new File(parent,child));
	}
	
}
