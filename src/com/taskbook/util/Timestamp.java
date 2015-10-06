package com.taskbook.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timestamp {
	public final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	public static java.sql.Timestamp getTimeStamp(String date, String time) {
		Calendar c = null;
		Date due_date = null;
		
		String timeStamp = date+" "+time;
		
		try {
			due_date = df.parse(timeStamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c = Calendar.getInstance();
		c.setTime(due_date);
		return new java.sql.Timestamp(c.getTimeInMillis());
	}
}