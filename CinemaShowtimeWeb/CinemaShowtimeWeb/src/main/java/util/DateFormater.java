package util;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormater {

	public static Date formatDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			Date formattedDate  = df.parse(date);
			System.out.println("date:" + formattedDate);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		    System.out.println(sdf.format(formattedDate));
		    
			return formattedDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
