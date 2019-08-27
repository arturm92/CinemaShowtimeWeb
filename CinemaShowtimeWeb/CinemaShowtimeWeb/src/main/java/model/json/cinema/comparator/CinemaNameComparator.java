package model.json.cinema.comparator;

import java.util.Comparator;

import model.json.cinema.Cinema;

public class CinemaNameComparator implements Comparator<Cinema> {

	@Override
	public int compare(Cinema o1, Cinema o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
