package org.ccffee.utils;

import org.ccffee.utils.exceptions.DateTimeParseException;
import org.ccffee.utils.iteration.ArrayUtils;

import java.time.*;

public class DateFormat {
	public static final byte YEAR = 0;
	public static final byte MONTH = 1;
	public static final byte DAY = 2;
	public static final byte HOUR = 3;
	public static final byte MINUTE = 4;
	public static final byte SECOND = 5;

	public static final DateFormat ISO_DATE = new DateFormat(YEAR,MONTH,DAY).setDelimiter("-");
	public static final DateFormat ISO_TIME = new DateFormat(HOUR,MINUTE,SECOND);

	private String delimiter = ":";
	
	private byte[] format = new byte[0];
	public DateFormat(byte... u) {
		add(u);
	}
	public DateFormat add(byte... u) {
		int l = format.length;
		format = ArrayUtils.expand(format, u.length);
		for(int i = 0; i<u.length;i++ )format[l+i]=u[i];
		return this;
	}
	public DateFormat setDelimiter(String delimiter) {
		this.delimiter = delimiter;
		return this;
	}

	public DateFormat clear() {
		format = new byte[0];
		return this;
	}
	public String formatLocal(LocalDateTime time) {
		String out = "";
		for(byte b : format)switch (b){
			case YEAR:
				out+=time.getYear()+delimiter;
				break;
			case MONTH:
				out+=time.getMonthValue()+delimiter;
				break;
			case DAY:
				out+=time.getDayOfMonth()+delimiter;
				break;
			case HOUR:
				out+=time.getHour()+delimiter;
				break;
			case MINUTE:
				out+=time.getMinute()+delimiter;
				break;
			case SECOND:
				out+=time.getSecond()+delimiter;
				break;
		}
		return out.substring(0, out.length()-delimiter.length());
	}
	public String formatLocal(long t){
		return formatLocal(LocalDateTime.from(Instant.ofEpochMilli(t)));
	}
	public String format(OffsetDateTime time) {
		String out = "";
		for(byte b : format)switch (b){
			case YEAR:
				out+=time.getYear()+delimiter;
				break;
			case MONTH:
				out+=time.getMonthValue()+delimiter;
				break;
			case DAY:
				out+=time.getDayOfMonth()+delimiter;
				break;
			case HOUR:
				out+=time.getHour()+delimiter;
				break;
			case MINUTE:
				out+=time.getMinute()+delimiter;
				break;
			case SECOND:
				out+=time.getSecond()+delimiter;
				break;
		}
		return out.substring(0, out.length()-delimiter.length());
	}
	public String format(long t,ZoneId zone){
		return format(OffsetDateTime.ofInstant(Instant.ofEpochMilli(t),zone));
	}
	public OffsetDateTime parse(String s, ZoneOffset zone){
		int year=1970,month=1,day=1,hour=0,minute=0,second=0;
		String[] strings = s.split(delimiter);
		int min = Integer.min(format.length,strings.length);
		try {
			for (int i = 0; i < min; i++)
				switch (format[i]) {
					case YEAR:
						year=Integer.parseInt(strings[i]);
						break;
					case MONTH:
						month=Integer.parseInt(strings[i]);
						break;
					case DAY:
						day=Integer.parseInt(strings[i]);
						break;
					case HOUR:
						hour=Integer.parseInt(strings[i]);
						break;
					case MINUTE:
						minute=Integer.parseInt(strings[i]);
						break;
					case SECOND:
						second=Integer.parseInt(strings[i]);
						break;
				}
		}catch(Exception e){
			throw new DateTimeParseException(e);
		}
		try{
			return OffsetDateTime.of(year,month,day,hour,minute,second,0,zone);
		}catch (Exception e){
			throw new DateTimeParseException(e);
		}
	}
	public long parseAsLong(String s,ZoneOffset zone){
		return parse(s,zone).toInstant().toEpochMilli();
	}

	public static String formatIsoDateTime(long time, ZoneOffset zone){
		return DateFormat.ISO_DATE.format(time,zone)+"T"+DateFormat.ISO_TIME.format(time,zone);
	}
	public static long parseIsoDateTime(String time, ZoneOffset zone){
		String[] s = time.split("T");
		if(s.length!=2)throw new DateTimeParseException("wrong time format");
		return DateFormat.ISO_DATE.parseAsLong(s[0],zone)+DateFormat.ISO_TIME.parseAsLong(s[1],ZoneOffset.UTC);
	}

}
