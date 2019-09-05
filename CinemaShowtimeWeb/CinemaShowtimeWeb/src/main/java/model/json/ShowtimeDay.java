package model.json;

import java.util.List;

import cinemaShowtime.utils.DateFormater;

public class ShowtimeDay {

	private String date;
	private List<ShowtimeHour> hoursList;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ShowtimeHour> getHoursList() {
		return hoursList;
	}

	public void setHoursList(List<ShowtimeHour> hoursList) {
		this.hoursList = hoursList;
	}

	public String getDay() {
		DateFormater df = new DateFormater();
		return df.getDay(date);
	}
}
