package com.xuehai.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author: zhangcong
 * @date: 2018/3/30 13:14
 * @describe:
 */
@Slf4j
public class DateTimeUtil {

    public static final String DATE_LONG_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_ALL_KEY_STR = "yyyyMMddHHmmss";
    public static final String DATE_YEAR_MONTH = "yyyyMM";
    public static final String DATE_YEAR_DAY = "yyyyMMdd";


    /**
     * 当前时间
     *
     * @return
     */
    public static long nowDate() {
        return millisTosecond(DateTime.now().getMillis());
    }

    /**
     * description: 给制定的时间加上(减去)天
     * author:zhangcong
     * date:2017/3/9 16:36
     * param: date 日期
     * param: pattern 格式
     * param: num 加(减)天数
     */
    public static String addDay(Date date, String pattern, int num) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusDays(num).toString(pattern);
    }

    /**
     * description:当天的开始时间
     * author:zhangcong
     * date:2017/3/9 16:41
     */
    public static long startOfTodDay() {
        return DateTime.now().withMillisOfDay(0).getMillis();
    }

    /**
     * @author zhangcong
     * @date 20:08 2017/10/26
     * @description 当天的结束时间
     */
    public static long endOfTodDay() {
        return DateTime.now().withMillisOfDay(86399000).getMillis();
    }


    /**
     * description:时间戳转化Date
     * author:zhangcong
     * date:2017/3/9 16:47
     */
    public static Date longToDate(long millSec) {
        Date date = new Date(millSec);
        return date;
    }

    /**
     * description: 时间戳转化String
     * author:zhangcong
     * date:2017/7/28 10:40
     */
    public static String longToString(long millSec) {
        return dateToString(new Date(millSec), DATE_LONG_STR);
    }

    /**
     * description:Date转String
     * author:zhangcong
     * date:2017/3/9 16:49
     */
    public static String dateToString(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.toString(pattern);
    }

    /**
     * description:String转Date
     * author:zhangcong
     * date:2017/3/10 15:11
     */
    public static Date stringToDate(String str, String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return format.parseDateTime(str).toDate();
    }

    /**
     * description:根据周几来获取当前阅读时长
     * author:zhangcong
     * date:2017/3/10 13:50
     */
    public static int getReadingTime() {
        int readTime = 0;
        int weekday = DateTime.now().getDayOfWeek();
        switch (weekday) {
            case 1:
                readTime = 30;
                break;
            case 2:
                readTime = 30;
                break;
            case 3:
                readTime = 30;
                break;
            case 4:
                readTime = 30;
                break;
            case 5:
                readTime = 30;
                break;
            case 6:
                readTime = 60;
                break;
            case 7:
                readTime = 60;
                break;
            default:
                break;
        }
        return readTime;
    }

    /**
     * param date   当前时间
     * param amount 前或后几天 ： 1 表示后一天； -1 表示前一天
     * description: 获取 date 日期的前或后 amount 天
     * Since: 2017/3/10 13:50
     */
    public static Date currentDateAddDaysOfNumber(Date date, int amount) {
        if (null == date) {
            return null;
        }
        return new DateTime(date).plusDays(amount).toDate();
    }

    /**
     * param date   当前时间
     * param amount 前或后几天的23点59分59秒(1 表示后一天的;-1 表示前一天)
     * description: 获取 date 日期的前或后 amount 天
     * Since: 2017/3/10 13:50
     */
    public static Date format24DateAddDays(Date date, int amount) {
        if (null == date) {
            return null;
        }
        return new DateTime(date.getTime()).plusDays(amount).withField(DateTimeFieldType.secondOfDay(), 86399).toDate();
    }


    /**
     * description:Date转long
     * author:zhangcong
     * date:2017/3/10 14:31
     */
    public static long dateToLong(Date date) {
        return millisTosecond(date.getTime());
    }

    /**
     * description:毫秒数转化为分钟
     * author:zhangcong
     * date:2017/3/10 15:42
     */
    public static int millisecondTominute(long ms) {
        long minute = ms / (1000 * 60);
        return (int) minute;
    }

    /**
     * description:将毫秒数格式化天、小时、分钟、秒、毫秒
     * author:zhangcong
     * date:2017/3/10 15:58
     */
    public static String formatMillisecond(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    /**
     * description:获取当前年月
     * author:zhangcong
     * date:2017/6/26 16:10
     */
    public static long getDateYearMonth() {
        return Long.parseLong(dateToString(new Date(), DATE_YEAR_MONTH));
    }

    /**
     * Date 格式化 yyyyMM
     *
     * @param date
     * @return
     */
    public static long dateFormateYYYYMM(Date date) {
        String yearMonth = dateToString(date, DATE_YEAR_MONTH);
        if (StringUtils.isNotBlank(yearMonth)) {
            return Long.valueOf(yearMonth);
        }
        return 0;
    }

    /**
     * Date 格式化 yyyyMMdd
     *
     * @param date
     * @return
     */
    public static long dateFormateYYYYMMdd(Date date) {
        String yearMonth = dateToString(date, DATE_YEAR_DAY);
        if (StringUtils.isNotBlank(yearMonth)) {
            return Long.valueOf(yearMonth);
        }
        return 0;
    }

    /**
     * 计算2个时间相差多少分钟
     *
     * @param startTime
     * @param endTime
     * @param minute
     * @return true  相差小于 minute 分钟
     */
    public static boolean calculateTimeDifference(long startTime, long endTime, int minute) {
        int result = (int) ((endTime - startTime) / (1000 * 60));
        if (minute >= result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算2个日期相差多少天
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 天数
     */
    public static int calculateDay(long startTime, long endTime) {
        int result = (int) ((endTime - startTime) / (1000 * 3600 * 24));
        return result >= 0 ? result : 0;
    }

    /**
     * @param millis 毫秒
     * @author zhangcong
     * @date 19:59 2017/10/26
     * @description 忽略毫秒数
     */
    public static long millisTosecond(long millis) {
        if (millis <= 0 && String.valueOf(millis).length() > 3) {
            return 0;
        }
        String time = String.valueOf(millis);
        time = (time.substring(0, time.length() - 3)).concat("000");
        return Long.valueOf(time);
    }

    public static long dayTosecond() {
        return startOfTodDay();
    }

    /**
     * @param hour
     * @author HJQ
     * @date 15:05 2018/1/31
     * @description 给当前时间加上（减去） x小时
     */
    public static long addHour(int hour) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR_OF_DAY, minute);
//        Date date = calendar.getTime();
        return DateTime.now().plusHours(hour).getMillis();
    }

}
