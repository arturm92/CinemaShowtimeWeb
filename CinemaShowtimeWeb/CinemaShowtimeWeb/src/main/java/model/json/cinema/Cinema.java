package model.json.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseModel;

public class Cinema extends BaseModel {

	private String slug;
	private String telephone;
	private String website;

	@JsonProperty("location")
	private Location location;
	@JsonProperty("booking_type")
	private String bookingType;
	@JsonProperty("chain_id")
	private Long chainId;
	@JsonProperty("city_id")
	private Long cityId;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Location getLoaction() {
		return location;
	}

	public void setLoaction(Location loaction) {
		this.location = loaction;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Long getChainId() {
		return chainId;
	}

	public void setChainId(Long chainId) {
		this.chainId = chainId;
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
