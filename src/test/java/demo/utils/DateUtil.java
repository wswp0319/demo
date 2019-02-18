package demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat YYYYMMDD24HHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyyMM");

	/**
	 * 根据给的日期到底时间
	 * 
	 * @param date
	 * @param gapMinute
	 *            负数标识过去的时间，正数表示将来的时间
	 * @return
	 */
	public static Date getDateByGapMinute(Date date, int gapMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, gapMinute);
		
		int second=cal.get(Calendar.SECOND);
		//System.out.println(second);

		return cal.getTime();
	}

	public static Date getDateByGapMonth(Date date, int gapMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, gapMonth);

		return cal.getTime();
	}

	public static Date getDateByGapDay(Date date, int gapDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, gapDay);

		return cal.getTime();
	}

	public static int getYearMonth(Date date) {
		return Integer.parseInt(YYYYMM.format(date));
	}

	public static long getDate(Date date) {
		return Long.parseLong(YYYYMMDD24HHMMSS.format(date));
	}

	public static void main(String[] args) {

			//Date d = YYYYMMDD24HHMMSS.parse("20180501000102");
			Date d = new Date();
			d = getDateByGapMinute(d, -5);
			System.err.println(YYYYMMDD24HHMMSS.format(d));

			d = getDateByGapMonth(d, -1);
			System.err.println(YYYYMMDD24HHMMSS.format(d));

			d = getDateByGapDay(d, -1);
			System.err.println(YYYYMMDD24HHMMSS.format(d));

			System.out.println(getYearMonth(d));

			System.out.println(getDate(d));

	}
}
