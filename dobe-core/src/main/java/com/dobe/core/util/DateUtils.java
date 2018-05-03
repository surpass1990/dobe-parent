package com.dobe.core.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @Description : 日期工具类
 * @Project : framework-core
 * @Program Name : com.yirun.framework.core.utils.DateUtils.java
 * @Author : imzhousong@gmail.com 周松
 */
public abstract class DateUtils {

	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String DATE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_HH_MM_SS_Z1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String DATE_HH_MM_SS_Z2 = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String DATE_HH_MM_SS_Z3 = "yyyy-MM-dd'T'HH:mm:ssz";

	public static final String DATE_HH_MM_SS_A = "MM/dd/yyyy HH:mm:ss a";
	public static final String DATE_HHMMSS = "yyyyMMddHHmmss";

	private static List<DateFormat> formats = new ArrayList<DateFormat>();

	static {
		/** alternative formats */
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS));
		formats.add(new SimpleDateFormat(DATE_HH_MM));
		formats.add(new SimpleDateFormat(DATE));
		/** ISO formats */
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z1));
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z2));
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_Z3));
		formats.add(DateFormat.getDateTimeInstance());
		/** XPDL examples format */
		formats.add(new SimpleDateFormat(DATE_HH_MM_SS_A, Locale.US));
		formats.add(new SimpleDateFormat(DATE_HHMMSS));
		/** Only date, no time */
		formats.add(DateFormat.getDateInstance());
	}

	/**
	 * @Description:字符串转日期
	 */
	public synchronized static Date parse(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			return null;
		}
		for (DateFormat format : formats) {
			try {
				return format.parse(dateString);
			} catch (ParseException e) {

			}
		}
		return null;
	}

	/**
	 *  * @Description:根据格式字符串转日期
	 */
	public static Date parse(String dateString, String pattern) {
		if (StringUtils.isBlank(dateString) || StringUtils.isBlank(pattern)) {
			return null;
		}
		DateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Description:日期类型转字符串
	 */
	public synchronized static String format(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		return df.format(date);
	}

	/**
	 * @Description:日期类型转字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null)
			return "";
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * @Description:将某个时间范围按天进行切分，未满一天的按一天算
	 */
	public static List<Date> splitByDay(Date startDate, Date endDate) {
		List<Date> dayList = new ArrayList<Date>();
		String startDateStr = DateFormatUtils.format(startDate, DATE);
		Date startDate1 = DateUtils.parse(startDateStr);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate1);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(startDate1);
		tempCal.add(Calendar.DAY_OF_MONTH, 1);

		while (tempCal.before(endCal)) {
			dayList.add(startCal.getTime());
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			tempCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		dayList.add(startCal.getTime());
		return dayList;
	}

	/**
	 * @Description:判断两个时间是否在同一天内
	 * @param date1
	 *            时间
	 * @param date2
	 *            时间
	 * @return true=同一天；false=非同一天；
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		String date1Str = DateFormatUtils.format(date1, DATE);
		String date2Str = DateFormatUtils.format(date2, DATE);
		if (date1Str.equals(date2Str)) {
			return true;
		}
		return false;
	}

	/**
	 * @Description:判断两个时间是否在同一个月内
	 * @param date1
	 *            时间
	 * @param date2
	 *            时间
	 * @return true=在同一个月内；false=不在同一个月内；
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * @Description:判断两个时间是否在同一季度里
	 * @param date1
	 *            时间
	 * @param date2
	 *            时间
	 * @return true=在同一个季度内；false=不在同一个季度内；
	 */
	public static boolean isSameQuarter(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int month1 = cal1.get(Calendar.MONTH);
		int month2 = cal2.get(Calendar.MONTH);
		if (((month1 >= Calendar.JANUARY && month1 <= Calendar.MARCH)
				&& (month2 >= Calendar.JANUARY && month2 <= Calendar.MARCH))
				|| ((month1 >= Calendar.APRIL && month1 <= Calendar.JUNE)
						&& (month2 >= Calendar.APRIL && month2 <= Calendar.JUNE))
				|| ((month1 >= Calendar.JULY && month1 <= Calendar.SEPTEMBER)
						&& (month2 >= Calendar.JULY && month2 <= Calendar.SEPTEMBER))
				|| ((month1 >= Calendar.OCTOBER && month1 <= Calendar.DECEMBER)
						&& (month2 >= Calendar.OCTOBER && month2 <= Calendar.DECEMBER))) {
			return true;
		}
		return false;
	}

	/**
	 * @Description:得到两个时间的差额
	 */
	public static long betDate(Date date, Date otherDate) {
		return date.getTime() - otherDate.getTime();
	}

	/**
	 * @Description : 获得当天最后时间
	 * @Method_Name : getLastTimeOfDay
	 * @return
	 * @return : Timestamp
	 * @Creation Date : 2017年6月9日 上午10:26:48
	 * @Author : zhichaoding@hongkun.com zc.ding
	 */
	public static Timestamp getLastTimeOfDay() {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 23:59:59");
		return ts;

	}

	/**
	 * 
	 * @Description : 获得当天最早时间
	 * @Method_Name : getFirstTimeOfDay
	 * @return
	 * @return : Timestamp
	 * @Creation Date : 2017年7月13日 下午4:10:58
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static Timestamp getFirstTimeOfDay() {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 00:00:00");
		return ts;

	}

	/**
	 * 
	 * @Description : 获得指定日期最后时间
	 * @Method_Name : getLastTimeOfDay
	 * @param day
	 * @return
	 * @return : Timestamp
	 * @Creation Date : 2017年7月19日 上午10:51:04
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static Timestamp getLastTimeOfDay(Date day) {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 23:59:59");
		return ts;

	}

	/**
	 * 
	 * @Description : 获得指定日期最早时间
	 * @Method_Name : getFirstTimeOfDay
	 * @return
	 * @return : Timestamp
	 * @Creation Date : 2017年7月13日 下午4:10:58
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static Timestamp getFirstTimeOfDay(Date day) {
		SimpleDateFormat df = new SimpleDateFormat(DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 00:00:00");
		return ts;

	}

	/**
	 * @Description:获取当前日期毫秒值
	 */
	public static long getCurrentTime() {
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @Description:获取当前日期
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * @Description : 获得当前时间
	 * @Method_Name : getCurrentDate
	 * @param pattern
	 * @return : String
	 * @Creation Date : 2018年3月29日 下午3:22:53
	 * @Author : zhichaoding@hongkun.com zc.ding
	 */
	public static String getCurrentDate(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * @Description : 获得当前时间的yyyy-MM-dd HH:mm:ss字符串
	 * @Method_Name : getDefaultCurrentDate
	 * @return
	 * @return : String
	 * @Creation Date : 2018年3月29日 下午3:24:52
	 * @Author : zhichaoding@hongkun.com zc.ding
	 */
	public static String getDefaultCurrentDate() {
		return format(new Date(), DATE_HH_MM_SS);
	}

	/**
	 * @Description:获取当前日期
	 */
	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	public static String calendarToString(Calendar calendar, String template) {
		String stringCalendar = template;
		stringCalendar = stringCalendar.replace("{year}", String.valueOf(calendar.get(Calendar.YEAR)));
		stringCalendar = stringCalendar.replace("{month}", String.valueOf(calendar.get(Calendar.MONTH)));
		stringCalendar = stringCalendar.replace("{date}", String.valueOf(calendar.get(Calendar.DATE)));
		stringCalendar = stringCalendar.replace("{hour}", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
		stringCalendar = stringCalendar.replace("{minute}", String.valueOf(calendar.get(Calendar.MINUTE)));
		stringCalendar = stringCalendar.replace("{second}", String.valueOf(calendar.get(Calendar.SECOND)));
		stringCalendar = stringCalendar.replace("{millisecond}", String.valueOf(calendar.get(Calendar.MILLISECOND)));
		return stringCalendar;
	}

	/**
	 * 比较时间差 1小时内的显示：** 分钟前，例如：25 分钟前 1小时前（１天内的）：今天 **时：**分，例如： 08：17
	 * 1天前的（当前年）：*月*号 **时：**分，例如：05-09 23：58 当前年之前的：年－月－日 **时：**分，例如：2009-09-26
	 * 16：33
	 **/
	public static String timeCompare(Date _now, Date _date) {
		Calendar now = Calendar.getInstance();
		now.setTime(_now);
		Calendar date = Calendar.getInstance();
		date.setTime(_date);

		int nowY = now.get(Calendar.YEAR);
		int dateY = date.get(Calendar.YEAR);

		int nowM = now.get(Calendar.MONTH);
		int dateM = date.get(Calendar.MONTH);

		int nowD = now.get(Calendar.DAY_OF_MONTH);
		int dateD = date.get(Calendar.DAY_OF_MONTH);

		long l = now.getTimeInMillis() - date.getTimeInMillis();
		long m = nowM - dateM;
		long day = nowD - dateD;

		int dateHour = date.get(Calendar.HOUR_OF_DAY);
		int dateMinutes = date.get(Calendar.MINUTE);

		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long y = nowY - dateY;
		String ret = "";
		if (y > 0)// 大于一年的
			ret += (dateY + 1900) + "-";
		if (day > 0 || y > 0 || m > 0) {// 大于一天的
			if (dateM + 1 < 10)
				ret += "0";
			ret += (dateM + 1) + "-";
			if (dateD < 10)
				ret += "0";
			ret += dateD + " ";
		}
		if (hour > 0 || day > 0 || y > 0 || m > 0) {// 大于一小时
			if (dateHour < 10)
				ret += "0";
			ret += dateHour + ":";
			if (dateMinutes < 10)
				ret += "0";
			ret += dateMinutes;
		}
		if (y == 0 && (day * 24 + hour) == 0 && min != 0)
			ret = min + " 分前";
		if (y == 0 && (day * 24 + hour) == 0 && min == 0)
			ret = "1  分前";
		return ret;
	}

	public static long getFormatedTime(long time) {
		return getFormatedTime(new Date(time));
	}

	public static long getFormatedTime(Date date) {
		if (date == null) {
			return 0;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return new Long(dateFormat.format(date)).longValue();
	}

	/**
	 * @Description : 获得两个时间间隔天数
	 * @Method_Name : getDaysBetween
	 * @param start
	 * @param end
	 * @return : int
	 * @Creation Date : 2017年6月30日 下午3:31:56
	 * @Author : zhichaoding@hongkun.com zc.ding
	 */
	public static int getDaysBetween(Date start, Date end) {
		long bet = 0L;
		try {
			Date bigDate = end;
			Date smallDate = start;
			if (start.after(end)) {
				smallDate = end;
				bigDate = start;
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE);
			String sBigDate = simpleDateFormat.format(bigDate);
			String sSmallDate = simpleDateFormat.format(smallDate);
			bigDate = simpleDateFormat.parse(sBigDate);
			smallDate = simpleDateFormat.parse(sSmallDate);
			bet = betDate(bigDate, smallDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) (bet / 86400000);
	}

	/**
	 * @Description : 增加月份
	 * @Method_Name : addMonth
	 * @param @param
	 *            beginDate
	 * @param @param
	 *            addMonth
	 * @param @return
	 * @return : Date
	 * @Author : xuhuiliu@hongkun.com.cn 刘旭辉
	 * @date ：2017年6月27日
	 */
	public static Date addMonth(Date beginDate, int addMonth) {
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.add(Calendar.MONTH, addMonth);
		return date.getTime();
	}

	/**
	 * @Description : 增加天数
	 * @Method_Name : addDays
	 * @param @param
	 *            beginDate
	 * @param @param
	 *            addDays
	 * @param @return
	 * @return : Date
	 * @Author : xuhuiliu@hongkun.com.cn 刘旭辉
	 * @date ：2017年6月27日
	 */
	public static Date addDays(Date beginDate, int addDays) {
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.add(Calendar.DATE, addDays);
		return date.getTime();
	}

	/**
	 * @Description : 将time + suffix的Date返回
	 * @Method_Name : parseDate
	 * @param @param
	 *            time 年月日
	 * @param @param
	 *            suffix 时分秒
	 * @return : Date
	 * @Creation Date : 2017年07月10日 上午午09:36:50
	 * @Author : pengwu@hongkun.com.cn 吴鹏
	 */
	public static Date parseDate(String time, String suffix) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_HH_MM_SS);
		Date tempDate = null;
		if (!"".equals(time)) {
			try {
				if (!"".equals(suffix)) {
					tempDate = dateFormat.parse(time + " " + suffix);
				} else {
					tempDate = dateFormat.parse(time);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return tempDate;
	}

	/**
	 * 
	 * @Description : 毫秒转化 时间
	 * @Method_Name : formatTime
	 * @param ms
	 * @return
	 * @return : String
	 * @Creation Date : 2017年8月10日 上午11:51:55
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static String formatTime(long ms) {

		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strDay + " 天" + strHour + " 小时" + strMinute + " 分钟 " + strSecond + " 秒";
	}

	/**
	 * 
	 * @Description :判断当前时间是否在有效时间范围内
	 * @Method_Name : isValidTime
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return : Boolean
	 * @Creation Date : 2018年3月8日 上午11:20:44
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static Boolean isValidTime(String startTime, String endTime) {
		String current = formatTime(getCurrentTime());
		if (startTime.compareTo(endTime) >= 0) {
			if (current.compareTo(startTime) >= 0) {
				return true;
			}
			if (current.compareTo(startTime) <= 0 && current.compareTo(endTime) <= 0) {
				return true;
			}
		}
		if (startTime.compareTo(endTime) <= 0) {
			if (current.compareTo(startTime) >= 0 && current.compareTo(endTime) <= 0) {
				return true;
			}
		}
		return false;
	}

	/***
	 * 
	 * @Description : 获取两个时间间隔
	 * @Method_Name : getLimitSecond
	 * @param start：格式HH:mm:ss
	 * @param end：格式HH:mm:ss
	 * @return
	 * @return : long
	 * @Creation Date : 2018年3月8日 上午11:40:06
	 * @Author : caoxinbang@hongkun.com.cn 曹新帮
	 */
	public static long betDate(String start, String end) {
		start = start.trim();
		end = end.trim();
		String todayStr = format(new Date(), DATE);
		Date tomorrowStr = addDays(new Date(), 1);
		Date todayLimit = parse(todayStr + " " + end, DATE_HH_MM_SS);
		Date tomorrowLimit = parse(format(tomorrowStr, DATE) + " " + end, DATE_HH_MM_SS);
		Date currentTime = new Date();
		String current = formatTime(getCurrentTime());
		if (start.compareTo(end) >= 0) {
			if (current.compareTo(start) >= 0) {
				return betDate(tomorrowLimit, currentTime);
			}
			if (current.compareTo(start) <= 0 && current.compareTo(end) <= 0) {
				return betDate(todayLimit, currentTime);
			}
		}
		if (start.compareTo(end) <= 0) {
			if (current.compareTo(start) >= 0 && current.compareTo(end) <= 0) {
				return betDate(todayLimit, currentTime);
			}
		}
		return 0;
	}

	/**
	 * @Description : 获取每个月第一天零点，如果参数为null 默认当前日期
	 * @Method_Name : getFirstDayOfMonth;
	 * @param date
	 * @return
	 * @return : Timestamp;
	 * @Creation Date : 2017年7月16日 下午8:16:52;
	 * @Author : yanbinghuang@hongkun.com.cn 黄艳兵;
	 */
	public static Timestamp getFirstDayOfMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE);
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		}
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 00:00:00");
		return ts;
	}

	/**
	 * @Description : 获取每个月最后一天最晚时间点 如果参数为null 默认当前日期
	 * @Method_Name : getLastDayOfMonth;
	 * @param date
	 * @return
	 * @return : Timestamp;
	 * @Creation Date : 2017年7月16日 下午8:17:22;
	 * @Author : yanbinghuang@hongkun.com.cn 黄艳兵;
	 */
	public static Timestamp getLastDayOfMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE);
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		String dayFormat = df.format(calendar.getTime());
		Timestamp ts = Timestamp.valueOf(dayFormat + " 23:59:59");
		return ts;
	}

	public static void main(String[] args) {

//		System.out.println(addDays(DateUtils.parse("2017-03-01"), -1));
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(3);
		BigDecimal c = new BigDecimal(3);
		System.out.println(a.divide(b.multiply(c),2,BigDecimal.ROUND_HALF_UP));
		System.out.println(a.divide(b,2,BigDecimal.ROUND_HALF_UP).divide(c, 2, BigDecimal.ROUND_HALF_UP));

	}
}