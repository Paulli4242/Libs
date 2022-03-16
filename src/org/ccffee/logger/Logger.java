package org.ccffee.logger;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class Logger {
	
	private static Logger logger;
	private HashMap<String, IFormatable> logStreams = new HashMap<>();
	public Logger() {
	}
	@SuppressWarnings("resource")
	public Logger(boolean debug) {
		addLogStream("info",new LogStream("[INFO] ",new FileOutputStream(FileDescriptor.out)));
		addLogStream("error", new LogStream("[ERROR] ", new FileOutputStream(FileDescriptor.err)));
		addLogStream("warn", new LogStream("[WARN] ", new FileOutputStream(FileDescriptor.out)));
		if(debug)addLogStream("debug", new LogStream("[DEBUG] ", new FileOutputStream(FileDescriptor.out)));
	}
	public Logger setAsOut() {
		IFormatable stream = getInfoStream();
		if(stream instanceof PrintStream)System.setOut((PrintStream) stream);
		return this;
	}
	public Logger setAsErr() {
		IFormatable stream = getErrorStream();
		if(stream instanceof PrintStream)System.setErr((PrintStream) stream);
		return this;
	}
	public Logger addLogStream(String type, IFormatable stream) {
		logStreams.put(type.toLowerCase(), stream);
		return this;
	}
	public Logger removeLogStream(String type) {
		logStreams.remove(type);
		return this;
	}
	public Logger replaceLogStream(String type, LogStream stream) {
		logStreams.replace(type, stream);
		return this;
	}
	public IFormatable getLogStream(String type) {
		return logStreams.get(type.toLowerCase());
	}
	public IFormatable getInfoStream() {
		return getLogStream("info");
	}
	public IFormatable getErrorStream() {
		return getLogStream("error");
	}
	public IFormatable getWarnStream(){
		return getLogStream("warn");
	}
	public IFormatable getDebugStream(){
		return getLogStream("debug");
	}
	public void log(String s, Object... args) {
		log("info", s, args);
	}
	public void logInfo(String s, Object... args) {
		log("info", s, args);
	}
	public void logWarn(String s, Object... args) {
		log("warn", s, args);
	}
	public void logError(String s, Object... args) {
		log("error", s, args);
	}
	public void logDebug(String s, Object... args) {
		log("debug", s, args);
	}
	public void log(String type, String s, Object... args) {
		IFormatable log;
		if((log = getLogStream(type))!=null)log.formatln(s, args);
	}
	public static void resetDefaultOutputs() {
		resetErr();
		resetOut();
	}
	public static void resetErr() {
		System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
	}
	public static void resetOut() {
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	public static Logger createDefaultLogger(boolean debug) {
		logger = new Logger(debug);
		return logger;
	}
	public static Logger createLogger() {
		logger = new Logger();
		return logger;
	}
	public static Logger getLogger() {
		return logger!=null?logger:(logger = new Logger());
	}
	public static boolean exist() {
		return !getLogger().logStreams.isEmpty();
	}
}
