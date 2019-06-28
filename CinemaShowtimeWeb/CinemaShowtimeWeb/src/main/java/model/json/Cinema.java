package model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseModel;

public class Cinema extends BaseModel {

	/*
	 * { "id":"64754", "slug":"kino-gryf-miechow", "name":"Kino Gryf",
	 * "chain_id":null, "telephone":"+48 41 383 13 31",
	 * "website":"http://www.ckis.miechow.eu/kino", "location":{},
	 * "booking_type":"external" }
	 */

	private String slug;
	private String telephone;
	private String website;

	@JsonProperty("location")
	private Location location;
	@JsonProperty("booking_type")
	private String bookingType;
	@JsonProperty("chain_id")
	private Long chainId;

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

	public Long getChain_id() {
		return chainId;
	}

	public void setChain_id(Long chain_id) {
		this.chainId = chain_id;
	}

	public String getBooking_type() {
		return bookingType;
	}

	public void setBooking_type(String booking_type) {
		this.bookingType = booking_type;
	}

}
