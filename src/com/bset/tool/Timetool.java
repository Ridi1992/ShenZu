package com.bset.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.text.format.Time;
import android.util.Log;

/**
 * 获取当前系统时间
 *
 */
public class Timetool {
	/**
	 * 将时间戳转为代表"距现在多久之前"的字符串
	 * @param timeStr	时间戳
	 * @return
	 */
	public static String getStandardDate(String timeStr) {
		if(timeStr == null || timeStr.trim().equals("")){
			return null;
		}
		Log.i("时间戳===", ""+timeStr);
		StringBuffer sb = new StringBuffer();
		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t*1000);
		long mill = (long) Math.ceil(time /1000);//秒前

		long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

		long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

		long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟"); 
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}
	public static String gettime(){
		 Time time = new Time("GMT+8");       
	     time.setToNow();      
	     int year = time.year;      
	     int month = time.month;      
	     int day = time.monthDay;      
	     int minute = time.minute;      
	     int hour = time.hour;      
	     int sec = time.second;      
//	     myTextView.setText("当前时间为：" + year +       
//	                         "年 " + month +       
//	                         "月 " + day +       
//	                         "日 " + hour +       
//	                         "时 " + minute +       
//	                         "分 " + sec +       
//	                         "秒");  
		return year + 
				"- " + month +       
				"-" + day +       
				"\t " + hour + 
				":" + minute +       
				":" + sec;
	}
	
		public static int getyear(){
			 Time time = new Time("GMT+8");       
		     time.setToNow();      
		     int year = time.year;      
		     int month = time.month;      
		     int day = time.monthDay;      
		     int minute = time.minute;      
		     int hour = time.hour;      
		     int sec = time.second;      
//		     myTextView.setText("当前时间为：" + year +       
//		                         "年 " + month +       
//		                         "月 " + day +       
//		                         "日 " + hour +       
//		                         "时 " + minute +       
//		                         "分 " + sec +       
//		                         "秒");  
			return year;
		}
	public static long timetool(long day){
		long ll=(long) (day*24*60*60*1000.0f);
		System.out.println("day="+day+"时间戳="+ll);
		return ll;
	}
	 /**
	 * 获取当前时间
	 */
	public static String getnowtime(){
		 return trans_time( (long) (System.currentTimeMillis()/1000f));
	 }
	/**
	 * 将时间戳转换成字符串
	 */
	public static String trans_time(long time){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			long lcc_time = Long.valueOf(time); 
			String re_StrTime = sdf.format(new Date(time * 1000L));  
			return re_StrTime; 
		} 
		catch (Exception e) {
			return ""; 
		}
	}
	/**
	 *返回day天前的日期
	 */
	public static String daybefor(long day){
		long time1=(long) (System.currentTimeMillis()-timetool(day));//当前时间减去day天的时间
		return trans_time((long) (time1/1000f));
	}
	
	
	/**
	 *时间转换成时间戳
	 */
	public static long  getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
			try {
				d = sdf.parse(user_time);
				long l = d.getTime();
				String str = String.valueOf(l);
				re_time = str.substring(0, 10);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return Integer.parseInt(re_time);
		}
	/**
	 *时间转换成时间戳
	 */
	public static long  getTime1(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
			try {
				d = sdf.parse(user_time);
				long l = d.getTime();
				String str = String.valueOf(l);
				re_time = str.substring(0, 10);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return Integer.parseInt(re_time);
		}
}
