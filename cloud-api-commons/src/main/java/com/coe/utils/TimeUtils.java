package com.coe.utils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    /**
     * 在时间字符串格式为(yyyy-MM-dd) HH:mm(:ss)的情况下
     * 确认时间格式是否包含秒数（:ss）
     * @param timeString 时间字符串 格式应为yyyy-MM-dd HH:mm:ss
     * @return 时间字符串包含秒数则返回true，不包含秒数返回false
     */
    public static boolean checkSecond(String timeString){
        Pattern pattern = Pattern.compile(":");
        Matcher matcher = pattern.matcher(timeString);
        int count=0;
        while (matcher.find()){
            count++;
        }
        return count > 1;
    }
    public static String checkTimeT(String timeString){
        return new String(timeString).replace("T"," ");
    }

    public static String fixFormat(String timeString){
        return null;
    }

    /**
     * 向时间字符串中添加 :00 秒数
     * @param timeString 时间字符串
     * @return 添加了(:00)的时间字符串
     */
    public static String appendSecond(String timeString){
        if (timeString!=null && !"".equals(timeString)){
            if (timeString.matches("[\\d]+-[\\d]+-[\\d]+[T|\\s][\\d]+:[\\d]+")){
                System.out.println("添加秒数...");
                return (timeString + ":00").replace("T"," ");
            }
        }
        return null;
    }

    /**
     * 将Date类型转换为String类型
     * @param timeDate Date型时间，格式可以为Date基础格式
     * @return 时间字符串，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String parseString(Date timeDate){
        if (timeDate!=null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeDate);
        }
        return null;
    }

    //转换成Time类型
    public static Time parseTime(Date date){
        if (date!=null){
            return new Time(date.getTime());
        }
        return null;
    }
    public static Time parseTime(String timeString){
        if (timeString!=null && !"".equals(timeString)) {
            timeString=checkTimeT(timeString);
            if (!checkSecond(timeString)){
                timeString=appendSecond(timeString);
            }
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return parseTime(date);
        }
        return null;
    }
    /**
     * 将时间字符串转为Date型时间
     * 如果时间字符串不包含秒数则添加
     * @param timeString 时间字符串，可以不包含秒数
     * @return Date型数据
     */
    public static Date parseDate(String timeString){
        if (timeString!=null && !"".equals(timeString)) {
            timeString=checkTimeT(timeString);
            if (!checkSecond(timeString)){
                timeString=appendSecond(timeString);
            }
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将Date类型数据转为Timestamp数据
     * @param timeDate Date型数据
     * @return Timestamp型数据，格式为yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp parseTimestamp(Date timeDate){
        if (timeDate!=null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return Timestamp.valueOf(simpleDateFormat.format(timeDate));
        }
        return null;
    }

    /**
     * 将时间字符串最终转为Timestamp型数据
     * 时间字符串先转为Date型数据，如果不包含秒数则为时间字符串添加秒数(:00)
     * 然后将Date型数据转为Timestamp型数据
     * @param timeString 时间字符串
     * @return Timestamp型数据，格式为yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp parseTimestamp(String timeString){
        return parseTimestamp(parseDate(timeString));
    }

    /**
     * 是否符合yyyy-MM-dd HH:mm:ss或yyyy-MM-ddTHH:mm:ss
     * @param timeString 时间字符串
     * @return 符合格式放回true，否则放回false
     */
    public static boolean checkFormat(String timeString){
        return timeString.matches("^[\\d]+-[\\d]+-[\\d]+[T|\\s][\\d]+:[\\d]+:[\\d]+$");
    }
    public static int getDayTime(String timeString){
        int day=0;
        if (checkFormat(timeString)){
            String[] strings = timeString.split("[\\s|T]");
            strings[0]= String.valueOf(new StringBuilder(strings[0]).reverse());
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(strings[0]);
            if (matcher.find()){
                day= Integer.parseInt(matcher.group());
            }
        }
        return day;
    }
    public static int getHourTime(String timeString){
        int hour=0;
        if (checkFormat(timeString)){
            String[] strings = timeString.split("[\\s|T]");
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(strings[1]);
            if (matcher.find()){
                hour= Integer.parseInt(matcher.group());
            }
        }
        return hour;
    }
    public static int getMinuteTime(String timeString){
        int minute=0;
        if (checkFormat(timeString)){
            String[] strings = timeString.split("[\\s|T]");
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(strings[1]);
            if (matcher.find()){
                if (matcher.find()){
                    minute= Integer.parseInt(matcher.group());
                }
            }
        }
        return minute;
    }
    public static int getSecondTime(String timeString){
        int second=0;
        if (checkFormat(timeString)){
            String[] strings = timeString.split("[\\s|T]");
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(strings[1]);
            if (matcher.find()){
                if (matcher.find()){
                    if (matcher.find()){
                        second= Integer.parseInt(matcher.group());
                    }
                }
            }
        }
        return second;
    }
    /**
     * 提取Timestamp型数据中的数字
     * @param timestamp 时间戳
     * @return 时间戳里的数字，eg：200200523160030
     */
    public static long parseLong(Timestamp timestamp){
        return parseLong(String.valueOf(timestamp));
    }

    /**
     * 提取Timestamp型数据转为的时间字符串中的数字
     * 并将这些数字转为Long型
     * @param timeString 时间字符串
     * @return 时间字符串里的数字，eg：200200523160030
     */
    public static long parseLong(String timeString){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(timeString);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()){
            stringBuilder.append(matcher.group());
        }
        return Long.parseLong(stringBuilder.toString());
    }

    /**
     * 判断时间先后
     * @param oldTimestamp old时间点
     * @param newTimestamp 与old时间点的判断
     * @return >0没到old时间点（old时间点更后）   <0已经进过了old时间点（old时间点更前）  =0刚好
     */
    public static long compareTime(Timestamp oldTimestamp,Timestamp newTimestamp){
        long oldTime = parseLong(oldTimestamp);
        long newTime = parseLong(newTimestamp);
        return oldTime - newTime;
    }

    /**
     * 判断时间先后
     * @param oldDate old时间点
     * @param newDate 与old时间点的判断
     * @return >0没到old时间点（old时间点更后）   <0已经进过了old时间点（old时间点更前）  =0刚好
     */
    public static long compareTime(Date oldDate,Date newDate){
        Timestamp oldTimestamp = parseTimestamp(oldDate);
        Timestamp newTimestamp = parseTimestamp(newDate);
        return compareTime(oldTimestamp,newTimestamp);
    }
    /**
     * 统计两个时间的时间差
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getDistanceTime(Date dateOne, Date dateTwe){
        if (dateOne==null||dateTwe==null){
            return null;
        }
        if (compareTime(dateOne,dateTwe)>1){
            Date temp;
            temp=dateOne;
            dateOne=dateTwe;
            dateTwe=temp;
        }
        long day;//天数差
        long hour;//小时数差
        long min;//分钟数差
        long second;//秒数差
        long diff;//毫秒差
        long timeOne = dateOne.getTime();
        long timeTwe = dateTwe.getTime();

        diff = timeTwe - timeOne;
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        second = ((diff/1000) - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("1970-01-");
        if (day!=0){
            stringBuilder.append(day).append(" ");
        }else {
            stringBuilder.append("01").append(" ");
        }
        stringBuilder.append(hour).append(":").append(min).append(":").append(second);
        return String.valueOf(stringBuilder);
    }
    /**
     * 统计两个时间的时间差
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getDistanceTime(String timeStringOne,String timeStringTwo){
        Date dateOne = parseDate(timeStringOne);
        Date dateTwe = parseDate(timeStringTwo);
        return getDistanceTime(dateOne,dateTwe);
    }

}
