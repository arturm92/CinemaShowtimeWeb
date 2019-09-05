package model.json.complex;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinemaShowtime.utils.DateFormater;
import model.json.Showtime;
import model.json.ShowtimeDay;
import model.json.ShowtimeHour;
import model.json.base.BaseComplexModel;

public class Showtimes extends BaseComplexModel implements JsonList<Showtime> {

	@JsonProperty("showtimes")
	private List<Showtime> list;

	@Override
	public List<Showtime> getList() {
		return list;
	}

	@Override
	public void setList(List<Showtime> list) {
		this.list = list;
	}

	@Override
	public void showAllElements() {
		for (Showtime showtime : list) {
			System.out.println("MovieId: " + showtime.getMovieId() + " StartAt" + showtime.getStartAt());
		}
	}

	public List<ShowtimeDay> getNormalizeList() {
		List<ShowtimeDay> showtimeDayList = new ArrayList<ShowtimeDay>();

		DateFormater df = new DateFormater();
		ShowtimeDay showtimeDay = null;
		for (Showtime showtime : list) {
			String showtimeDate = df.formatDateShort(showtime.getStartAt());
			ShowtimeHour showtimeHour = new ShowtimeHour();
			showtimeHour.setHour(df.formatTimeOnly(showtime.getStartAt()));
			showtimeHour.setBookingLink(showtime.getBookingLink());
			showtimeDay = createShowtimeDayList(showtimeDayList, showtimeDay, showtimeDate, showtimeHour);

		}
		showtimeDayList.add(showtimeDay);
		return showtimeDayList;
	}

	private ShowtimeDay createShowtimeDayList(List<ShowtimeDay> showtimeDayList, ShowtimeDay showtimeDay,
			String showtimeDate, ShowtimeHour showtimeHour) {
		List<ShowtimeHour> hoursList;
		if (showtimeDay == null) {
			showtimeDay = new ShowtimeDay();
			showtimeDay.setDate(showtimeDate);
		}

		if (showtimeDay.getHoursList() == null) {
			hoursList = new ArrayList<ShowtimeHour>();
			hoursList.add(showtimeHour);
			showtimeDay.setHoursList(hoursList);
		} else if (showtimeDay.getDate().equals(showtimeDate)) {
			hoursList = showtimeDay.getHoursList();
			hoursList.add(showtimeHour);
		} else if (!showtimeDay.getDate().equals(showtimeDate)) {
			showtimeDayList.add(showtimeDay);
			showtimeDay = createShowtimeDayList(showtimeDayList, null, showtimeDate, showtimeHour);
		}
		return showtimeDay;
	}

	public List<Showtime> findMovieShowtime(Long id) {
		List<Showtime> returnList = new ArrayList<Showtime>();
		for (Showtime showtime : list) {
			if (showtime.getMovieId() != null && showtime.getMovieId().compareTo(id) == 0) {
				returnList.add(showtime);
			}
		}
		return returnList;
	}
}
