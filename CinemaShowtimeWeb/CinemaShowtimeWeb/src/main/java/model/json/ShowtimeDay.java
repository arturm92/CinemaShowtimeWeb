package model.json;

import java.util.List;

public class ShowtimeDay {

	private String date;
	private List<String> hours;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getHours() {
		return hours;
	}
	public void setHours(List<String> hours) {
		this.hours = hours;
	}
}
