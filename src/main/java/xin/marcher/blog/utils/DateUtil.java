package xin.marcher.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取系统时间
 *
 * @author marcher
 */
public class DateUtil {

    /** 一天的时间毫秒值 */
    public static final Long DATE_TIMESTAMP = 86400000L;

    public static final String PATTERN_DIVIDE_DATE = "yyyy/MM/dd";
    public static final String PATTERN_DIVIDE_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_DIVIDE_MINUTE_TIME = "yyyy/MM/dd HH:mm";

    public static final String PATTERN_HYPHEN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_HYPHEN_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_HYPHEN_MINUTE_TIME = "yyyy-MM-dd HH:mm";

    /**
     * 当前时间毫秒值
     */
    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 日期解析成时间戳
     *
     * @param dateTime 时间字符串
     * @param pattern  格式
     * @return 返回时间毫秒值
     */
    public static Long parseDate(String dateTime, String pattern) {
        if (EmptyUtil.isEmpty(dateTime)) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date data = null;
        try {
            data = sdf.parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return null;
        }
        return Long.valueOf(String.valueOf(data.getTime()));
    }

    /**
     * 日期格式化
     *
     * @param timeStamp 时间戳
     * @param pattern   格式
     * @return
     */
    public static String formatDate(Long timeStamp, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(timeStamp);
    }

    /**
     * 指定日期该天开始的时间
     */
    public static Long getDayBeginTime(String dateTime, String pattern) {
        return parseDate(dateTime, pattern);
    }

    /**
     * 指定日期该天结束的时间
     */
    public static Long getDayEndTime(String dateTime, String pattern) {
        if (EmptyUtil.isEmptyTrim(dateTime)) {
            return null;
        }
        return getDayBeginTime(dateTime, pattern) + 86400000;
    }
}
