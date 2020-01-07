package com.cognizant.truyum.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date convertToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date newDate = null;
		try {
			newDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	public static String displayDateFormat(Date dateOfLaunch) {
		DateFormat modifyDate = new SimpleDateFormat("dd/MM/yyyy");
		String formatDate = modifyDate.format(dateOfLaunch);
		return formatDate;
	}
}