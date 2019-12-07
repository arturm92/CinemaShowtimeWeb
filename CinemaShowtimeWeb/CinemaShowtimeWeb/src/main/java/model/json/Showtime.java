package model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinemaShowtime.utils.DateFormatter;

public class Showtime {
 
	private String id;
	@JsonProperty("cinema_id")
	private Long cinemaId;
	@JsonProperty("movie_id")
	private Long movieId;
	@JsonProperty("start_at")
	private String startAt;
	private String language;
	@JsonProperty("subtitle_language")
	private String subtitleLanguage;
	private String auditorium;
	@JsonProperty("is_3d")
	private String is3D;
	@JsonProperty("is_imax")
	private String isIMAX;
	@JsonProperty("booking_type")
	private String bookingType;
	@JsonProperty("booking_link")
	private String bookingLink;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public String getStartAt() {
		return startAt;
	}
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSubtitleLanguage() {
		return subtitleLanguage;
	}
	public void setSubtitleLanguage(String subtitleLanguage) {
		this.subtitleLanguage = subtitleLanguage;
	}
	public String getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(String auditorium) {
		this.auditorium = auditorium;
	}
	public String getIs3D() {
		return is3D;
	}
	public void setIs3D(String is3d) {
		is3D = is3d;
	}
	public String getIsIMAX() {
		return isIMAX;
	}
	public void setIsIMAX(String isIMAX) {
		this.isIMAX = isIMAX;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public String getBookingLink() {
		return bookingLink;
	}
	public void setBookingLink(String bookingLink) {
		this.bookingLink = bookingLink;
	}
	
	public String getFormattedDate() {
		DateFormatter df = new DateFormatter();
		return df.formatDateWithTimezone(getStartAt()); 
	}
	
}
