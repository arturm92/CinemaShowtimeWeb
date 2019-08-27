package model.json.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

/*	"location":
	{
			"lat":50.3563,
			"lon":20.0308,
			"address": {}
	}*/
	
	private String lat;
	private String lon;
	@JsonProperty("address")
	private Address address;
	
	public Location() {
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
