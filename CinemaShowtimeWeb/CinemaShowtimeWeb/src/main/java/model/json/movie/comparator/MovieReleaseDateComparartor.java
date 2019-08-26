package model.json.movie.comparator;

import java.util.Comparator;

import model.json.movie.MovieFormatted;

public class MovieReleaseDateComparartor implements Comparator<MovieFormatted> {

	@Override
	public int compare(MovieFormatted o1, MovieFormatted o2) {
		return o1.getReleaseDateInDateType().compareTo(o2.getReleaseDateInDateType());
	}

}
