package xyz.dc_stats.utils;
import xyz.dc_stats.utils.iteration.ArrayUtils;

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
}
