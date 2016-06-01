package util;


import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utilities
 *
 * @author Kyia
 */
public class DateUtils {

	public static final String DAFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DAFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Constructor
	 */
	public DateUtils() {

	}

	public static Date parseTimeStr(String timeStr) {
		if (org.apache.commons.lang.StringUtils.isEmpty(timeStr)) {
			return null;
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(DAFAULT_DATE_FORMAT).parse(timeStr);
		} catch (ParseException e1) {
			try {
				date = new SimpleDateFormat(DAFAULT_TIME_FORMAT).parse(timeStr);
			} catch (ParseException e2) {
				date = new Date(Long.parseLong(timeStr));
			}
		}
		return date;
	}

	/**
	 * Format time with default time pattern
	 *
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {
		return new SimpleDateFormat(DAFAULT_TIME_FORMAT).format(new Date(time));
	}

	/**
	 * Format time with default date pattern
	 *
	 * @param time
	 * @return
	 */
	public static String formatDate(long time) {
		return new SimpleDateFormat(DAFAULT_DATE_FORMAT).format(new Date(time));
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isEmpty(format)) {
			format = DateUtils.DAFAULT_TIME_FORMAT;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static String formatDate(Date date) {
		return formatDate(date, null);
	}

	/**
	 * Format time with pattern
	 *
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatTime(long time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(time));
	}

	/**
	 * Parse time
	 *
	 * @param str
	 * @return
	 */
	public static long parseDate(String str) throws ParseException {
		long time;
		try {
			Date date = new SimpleDateFormat(DAFAULT_DATE_FORMAT).parse(str);
			time = date.getTime();
		} catch (ParseException e) {
			throw e;
		}
		return time;
	}

	public static Date parseDateReturnDate(String str) throws ParseException {
		try {
			Date date = new SimpleDateFormat(DAFAULT_DATE_FORMAT).parse(str);
			return date;
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * Parse time
	 *
	 * @param str
	 * @return
	 */
	public static Date parseTimeReturnDate(String str) throws ParseException {
		try {
			Date date = new SimpleDateFormat(DAFAULT_TIME_FORMAT).parse(str);
			return date;
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * Parse time
	 *
	 * @param str
	 * @return
	 */
	public static Date parseTimeReturnFitDate(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			Date date = new SimpleDateFormat(DAFAULT_TIME_FORMAT).parse(str);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	public static long parseTime(String str) throws ParseException {
		long time;
		try {
			Date date = new SimpleDateFormat(DAFAULT_TIME_FORMAT).parse(str);
			time = date.getTime();
		} catch (ParseException e) {
			throw e;
		}
		return time;
	}

	/**
	 * Parse time
	 *
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static long parseTime(String str, String pattern) throws ParseException {
		long time = System.currentTimeMillis();
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = format.parse(str);
			time = date.getTime();
		} catch (ParseException e) {
			throw e;
		}
		return time;
	}

	/**
	 * Get zero time, a day's begin
	 *
	 * @param time
	 * @return
	 */
	public static long getZeroTime(long time) {
		// Get date string
		String str = new SimpleDateFormat(DAFAULT_DATE_FORMAT).format(new Date(time));

		long zeroTime = time;
		try {
			Date date = new SimpleDateFormat(DAFAULT_DATE_FORMAT).parse(str);
			zeroTime = date.getTime();
		} catch (ParseException e) {
			// e....
		}
		return zeroTime;
	}

	/**
	 * Get day interval
	 * end must bigger than start
	 * end > start
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDayInterval(long start, long end) {
		long startZeroTime = getZeroTime(start);
		long endZeroTime = getZeroTime(end);

		int day = (int) ((endZeroTime - startZeroTime) / (24 * 60 * 60 * 1000));
		return day;
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat(DAFAULT_TIME_FORMAT).format(new Date());
	}

}

