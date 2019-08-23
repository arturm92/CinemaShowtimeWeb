package util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormater {

	private HashMap<Integer, String> daysMapping = new HashMap<Integer, String>() {
		{
			put(2, "Poniedziałek");
			put(3, "Wtorek");
			put(4, "Środa");
			put(5, "Czwartek");
			put(6, "Piątek");
			put(7, "Sobota");
			put(1, "Niedziela");
		}
	};

	public String formatDateWithTimezone(String inputDate) {
		try {
			DateFormat inputDateFormat = new SimpleDateFormat(Consts.TIMEZONE_DATE_FORMAT);
			Date date = inputDateFormat.parse(inputDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			SimpleDateFormat sdf = new SimpleDateFormat(Consts.TIME_DATE_FORMAT);

			String output = sdf.format(date);
			output += " " + daysMapping.get(calendar.get(Calendar.DAY_OF_WEEK));

			return output;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String formatDateShort(String inputString) {
		try {
			DateFormat inputDateFormat = new SimpleDateFormat(Consts.TIMEZONE_DATE_FORMAT);
			Date date = inputDateFormat.parse(inputString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			SimpleDateFormat sdf = new SimpleDateFormat(Consts.SIMPLE_DATE_FORMAT);
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String formatDateShort(Date inputDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(Consts.SIMPLE_DATE_FORMAT);
		return sdf.format(inputDate);
	}

	public String recalculateDateByMonth(int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, amount);
		SimpleDateFormat sdf = new SimpleDateFormat(Consts.SIMPLE_DATE_FORMAT);
		return sdf.format(c.getTime());
	}
	
	public String formatTimeOnly(String inputString) {
		try {
			DateFormat inputDateFormat = new SimpleDateFormat(Consts.TIMEZONE_DATE_FORMAT);
			Date date = inputDateFormat.parse(inputString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			SimpleDateFormat sdf = new SimpleDateFormat(Consts.TIME_FORMAT);
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDay(String inputDate) {
		try {
			DateFormat inputDateFormat = new SimpleDateFormat(Consts.SIMPLE_DATE_FORMAT);
			Date date = inputDateFormat.parse(inputDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return daysMapping.get(calendar.get(Calendar.DAY_OF_WEEK));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String convertSimpleDateToTimezone(Date date) {
		DateFormat inputDateFormat = new SimpleDateFormat(Consts.TIMEZONE_DATE_FORMAT);
		return inputDateFormat.format(date);
	}
	
	public int getCurrentYear() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public Date parseString(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Consts.SIMPLE_DATE_FORMAT);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
