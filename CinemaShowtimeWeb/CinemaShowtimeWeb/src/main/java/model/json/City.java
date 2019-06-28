package model.json;

import model.json.base.BaseModel;

public class City extends BaseModel {
	/*
	 * {"id":"11521", "name":"Olsztyn", "slug":"Olsztyn", "lat":53.7754,
	 * "lon":20.4812, "country":"PL"},
	 */

	private String slug;
	private String lat;
	private String lon;
	private String country;
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
