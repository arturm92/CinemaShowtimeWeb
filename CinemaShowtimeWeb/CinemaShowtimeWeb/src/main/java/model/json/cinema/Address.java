package model.json.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

/*	"address":
	{
		"display_text":"Rac??awicka 10, 32-200 Miech??w",
		"street":"Rac??awicka",
		"house":"10",
		"zipcode":"32-200",
		"city":"Miech??w",
		"state":"Lesser Poland Voivodeship",
		"state_abbr":"Lesser Poland Voivodeship",
		"country":"Poland",
		"country_code":"PL"
	}*/

	private String street;
	private String house;
	private String city;
	private String state;
	private String country;
	
	@JsonProperty("display_text")
	private String displayText;
	@JsonProperty("zipcode")
	private String zipCode;
	@JsonProperty("state_abbr")
	private String stateAbbr;
	@JsonProperty("country_code")
	private String countryCode;
	
	public Address() {
	}
	
	public String getDisplay_text() {
		return displayText;
	}
	public void setDisplay_text(String display_text) {
		this.displayText = display_text;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry_code() {
		return countryCode;
	}
	public void setCountry_code(String country_code) {
		this.countryCode = country_code;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getZip_code() {
		return zipCode;
	}

	public void setZip_code(String zip_code) {
		this.zipCode = zip_code;
	}

	public String getState_abbr() {
		return stateAbbr;
	}

	public void setState_abbr(String state_abbr) {
		this.stateAbbr = state_abbr;
	}
	

}
