package com.michen.olaxueyuan.common.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mingge on 2015/8/12.
 */
public class DateUtils {

	private static final SimpleDateFormat _format = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat formatHSN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 返回指定格式的日期字符串。
	 *
	 * @param formatString ：为null时，使用"yyyy-MM-dd";
	 * @param time
	 * @return
	 */

	public static String getFormatDate(String formatString, Object time) {
		if (formatString != null) {
			return new SimpleDateFormat(formatString).format(time);
		} else {
			return _format.format(time);
		}
	}

	/**
	 * 判断当前日期是星期几 第一种方法
	 *
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */

	public static int dayForWeek(String pTime) {

		Calendar c = Calendar.getInstance();

		try {
			c.setTime(_format.parse(pTime));
			int dayForWeek;

			if (c.get(Calendar.DAY_OF_WEEK) == 1) {

				dayForWeek = 7;

			} else {

				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

			}

			return dayForWeek;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;


	}

	/**
	 * 返回当前日期对应的周一和周日
	 *
	 * @param currentDate
	 * @return
	 */
	public static String[] getFirstAndLastOfWeek(String currentDate) {
		String[] dateArray = new String[2];
		SimpleDateFormat returnFormat = new SimpleDateFormat("MM-dd");
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		try {
			c.setTime(_format.parse(currentDate));
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			dateArray[0] = returnFormat.format(c.getTime());

			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			dateArray[1] = returnFormat.format(c.getTime());
			return dateArray;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;


	}

	/**
	 * 获取当前日期之前六天加当前日期共七天的数据
	 *
	 * @param currentDate
	 * @return
	 */

	public static ArrayList<String> getStringDateBeforeOfWeek(long currentDate) {
		ArrayList<String> dateList = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		for (int i = -6; i <= 0; i++) {
			c.setTimeInMillis(currentDate);
			c.add(Calendar.DAY_OF_YEAR, i);
			dateList.add(_format.format(c.getTime()));
		}
		return dateList;
	}

	/**
	 * 获取当前月的第一天和最后一天。
	 *
	 * @param currentDate
	 * @return
	 */

	public static String[] getFirstAndLastOfMonth(String currentDate) {
		String[] dateArray = new String[2];
		SimpleDateFormat returnFormat = new SimpleDateFormat("MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(_format.parse(currentDate));
			c.set(Calendar.DATE, 1);
			dateArray[0] = returnFormat.format(c.getTime());

			c.add(Calendar.MONTH, 1);
			c.add(Calendar.DATE, -1);
			dateArray[1] = returnFormat.format(c.getTime());
			return dateArray;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取当前日期对应一月的字符串日期列表
	 *
	 * @param currentDate
	 * @return
	 */

	public static ArrayList<String> getStringDateOfMonth(String currentDate) {
		ArrayList<String> dateList = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(_format.parse(currentDate));
			for (int i = 1; i <= c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
				c.set(Calendar.DATE, i);
				dateList.add(_format.format(c.getTime()));
			}
			return dateList;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取给定日期前30天加上当前日期共31天的字符串日期列表
	 *
	 * @param currentDate
	 * @return
	 */

	public static ArrayList<String> getStringDateOfMonthBeforeDate(long currentDate) {
		ArrayList<String> dateList = new ArrayList<String>();
//        dateList.add(_format.format(currentDate));
		Calendar c = Calendar.getInstance();
		for (int i = -30; i <= 0; i++) {
			c.setTimeInMillis(currentDate);
			c.add(Calendar.DAY_OF_YEAR, i);
			dateList.add(_format.format(c.getTime()));
		}
		return dateList;
	}

	public static String formatTime(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		SimpleDateFormat df2 = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
		String timeString = time;
		Date now;
		try {
			now = new Date();
			Date date = df.parse(time);
			long l = now.getTime() - date.getTime();
			if (l <= 60 * 1000) {
				timeString = "刚刚";
			} else if (l < 60 * 60 * 1000) {
				timeString = l / (60 * 1000) + "分钟前";
			} else if (l < 24 * 60 * 60 * 1000) {
				timeString = l / (60 * 60 * 1000) + "小时前";
			} else if (l < 30 * 24 * 60 * 60 * 1000) {
				timeString = l / (24 * 60 * 60 * 1000) + "天前";
			} else {
				timeString = df2.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeString;
	}

	/**
	 * 获取年月日时分秒的月份
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(String date, int type) {
		int month = 0;
		int year = 0;
		try {
			Date parse = formatHSN.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			month = calendar.get(Calendar.MONTH);
			year = calendar.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (type) {
			default:
			case 1:
				return month + 1;
			case 2:
				return year;
		}
	}

	/**
	 * 距离考试还有多少天
	 *
	 * @return
	 */
	public static long getRemainsDay() {
		try {
			String examDate = "2017-12-23";//2017考试时间
			long examTime = _format.parse(examDate).getTime();
			long time = examTime - System.currentTimeMillis();
			long l = time / (24 * 60 * 60 * 1000);
			Logger.e("examTime=" + examTime + "time=" + time + "l==" + l);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 指定日期加上天数后的日期
	 *
	 * @param num     为增加的天数
	 * @param newDate 创建时间
	 * @return
	 * @throws ParseException
	 */
	public static String plusDay(int num) {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currdate = format.format(d);
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
		d = ca.getTime();
		String enddate = format.format(d);
		Logger.d("现在的日期是：" + currdate + "num=" + num + ";增加天数以后的日期：" + enddate);
		return enddate;
	}
}
