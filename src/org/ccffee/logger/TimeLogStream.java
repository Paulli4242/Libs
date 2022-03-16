package org.ccffee.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Locale;

import org.ccffee.utils.DateFormat;

public class TimeLogStream extends LogStream {

	DateFormat format;
	
	public TimeLogStream(String prefix, DateFormat format, File file, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(prefix, file, csn);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, File file) throws FileNotFoundException {
		super(prefix, file);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException {
		super(prefix, out, autoFlush, encoding);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, OutputStream out, boolean autoFlush) {
		super(prefix, out, autoFlush);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, OutputStream out) {
		super(prefix, out);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, String fileName, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(prefix, fileName, csn);
		this.format = format;
	}

	public TimeLogStream(String prefix, DateFormat format, String fileName) throws FileNotFoundException {
		super(prefix, fileName);
		this.format = format;
	}
	@Override
	public void print(String s) {
		super.print(format.formatLocal(LocalDateTime.now())+" "+s);
	}
	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		return format(l, this.format.formatLocal(LocalDateTime.now())+" "+format, args);
	}
	@Override
	public PrintStream format(String format, Object... args) {
		return super.format(this.format.formatLocal(LocalDateTime.now())+" "+format, args);
	}

	
}
