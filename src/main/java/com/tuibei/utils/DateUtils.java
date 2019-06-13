package com.tuibei.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 时间戳转YYYY-MM-DD HH:MI:SS
     * 10位
     * @return
     */
    public static  String secondamp2date(long timestamp){

        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp * 1000));
        return result;
    }

    /**
     * 时间戳转YYYY/MM/DD HH:MI
     * 10位
     * @return
     */
    public static  String secondamp2shortDate(long timestamp){

        String result = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(timestamp * 1000));
        return result;
    }
    /**
     * 时间戳转YYYY-MM-DD HH:MI:SS
     * 13位
     * @return
     */
    public static  String milltamp2date(long timestamp){
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        return result;
    }

    /**
     * 标准时间YYYY-MM-DD HH:MI:SS
     */
    public static String stableTime(){
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return result;
    }

    /**
     * 标准时间月YYYY-MM
     */
    public static String stableMonth(){
        String result = new SimpleDateFormat("yyyy-MM").format(new Date());
        return result;
    }

    /**
     * 标准时间日YYYY-MM
     */
    public static String stableDay(){
        String result = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return result;
    }
    /**
     * 月份
     * @param chazhi
     * @return
     */
    public static String getMonthTime(int chazhi){
        Calendar cal =Calendar.getInstance();
        cal.add(Calendar.MONTH,chazhi);
        String format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        return format;
    }

    /**
     * 年
     * @param chazhi
     * @return
     */
    public static String getYearTime(int chazhi){
        Calendar cal =Calendar.getInstance();
        cal.add(Calendar.YEAR,chazhi);
        String format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        return format;
    }

    /**
     * 日期
     * @param chazhi
     * @return
     */
    public static String getDayTime(int chazhi){
        Calendar cal =Calendar.getInstance();
        cal.add(Calendar.DATE,chazhi);
        String format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        return format;
    }

    /**
     * 时间转时间戳
     * @param s
     * @return
     * @throws Exception
     */
    public static String dateToStamp(String s) throws Exception{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 获取当前时间戳到毫秒
     * @return
     */
    public static String getTimeInMillis(){
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        return String.valueOf(timeInMillis);
    }
    /**
     * 获取当前时间戳到秒
     * @return
     */
    public static String getTimeInSecond(){
        long timeInSecond =Calendar.getInstance().getTimeInMillis()/1000;
        return String.valueOf(timeInSecond);
    }

    public static Long getTimeInSecond_long(){
        long timeInSecond =Calendar.getInstance().getTimeInMillis()/1000;
        return timeInSecond;
    }


    public static void main(String[] args) {
        String timeInSecond = DateUtils.getTimeInSecond();
        System.out.println(timeInSecond);
        System.out.println(secondamp2shortDate(Long.valueOf(timeInSecond)));
    }
}
