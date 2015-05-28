package de.schaeferban.lectures.util;

import javax.xml.datatype.XMLGregorianCalendar;

public class FormatUtil {
  public static String formatDuration(int seconds) {
    int h = seconds / 3600;
    int m = seconds % 3600 / 60;
    int s = seconds % 60;
    return ((h>=1 ? h+" h " : "")+(m>=1 ? m+" m " : "")+(s>=1 || m<=0 && h<=0 ? s+" s" : "")).trim();
  }

  public static String formatDateTime(XMLGregorianCalendar cal) {
    return String.format("%04d-%02d-%02d ", cal.getYear(), cal.getMonth(), cal.getDay())
      +formatTime(cal)
      +String.format(" %+03d%02d", cal.getTimezone()/60, cal.getTimezone()%60);
  }

  public static String formatTime(XMLGregorianCalendar cal) {
    StringBuilder builder = new StringBuilder(String.format("%02d:%02d", cal.getHour(), cal.getMinute()));
    if (cal.getSecond()>=1) {
      if (cal.getMillisecond()>=1) {
        builder.append(String.format(":%02d.%03d", cal.getSecond(), cal.getMillisecond()));
      } else {
        builder.append(String.format(":%02d", cal.getSecond()));
      }
    }
    return builder.toString();
  }
}
