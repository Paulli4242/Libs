package org.ccffee.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class LogStream extends PrintStream implements IFormatable{
	
	protected String prefix;
	
	public LogStream(String prefix,File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		this.prefix = prefix;
	}

	public LogStream(String prefix,File file) throws FileNotFoundException {
		super(file);
		this.prefix = prefix;
	}

	public LogStream(String prefix,OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		this.prefix = prefix;
	}

	public LogStream(String prefix,OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		this.prefix = prefix;
	}

	public LogStream(String prefix,OutputStream out) {
		super(out);
		this.prefix = prefix;
	}

	public LogStream(String prefix,String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		this.prefix = prefix;
	}

	public LogStream(String prefix,String fileName) throws FileNotFoundException {
		super(fileName);
		this.prefix = prefix;
	}

	@Override
	public void print(boolean b) {
		print(""+b);
	}

	@Override
	public void print(char c) {
		// TODO Auto-generated method stub
		super.print(""+c);
	}

	@Override
	public void print(char[] s) {
		print(""+s);
	}

	@Override
	public void print(double d) {
		print(""+d);
	}

	@Override
	public void print(float f) {
		print(""+f);
	}

	@Override
	public void print(int i) {
		print(""+i);;
	}

	@Override
	public void print(long l) {
		print(""+l);
	}

	@Override
	public void print(Object obj) {
		print(""+obj);
	}

	@Override
	public void print(String s) {
		super.print(prefix+s);
	}
	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		return super.format(l, prefix+format, args);
	}
	@Override
	public PrintStream format(String format, Object... args) {
		return super.format(prefix+format, args);
	}

	public void formatln(Locale l, String format, Object... args) {
		println(String.format(l, format, args));
	}
	public void formatln(String format, Object... args) {
		println(String.format(format, args));
	}
}
