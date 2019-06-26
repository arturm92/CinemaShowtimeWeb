package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Cinemas implements Serializable {

	private static final long serialVersionUID = 5328606126723614189L;

	private List<Cinema> cinemas;

	public Cinemas() {
	}

	public List<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

	@Override
	public String toString() {
		return "Cinemas: " + cinemas.toString();
	}
}
