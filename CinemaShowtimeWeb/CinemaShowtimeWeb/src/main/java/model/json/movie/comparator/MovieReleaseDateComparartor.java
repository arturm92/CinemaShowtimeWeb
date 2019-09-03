package model.json.movie.comparator;

import java.util.Comparator;

import model.json.movie.MovieFormatted;

public class MovieReleaseDateComparartor implements Comparator<MovieFormatted> {

	@Override
	public int compare(MovieFormatted o1, MovieFormatted o2) {
		if (o1.getReleaseDateInDateType() == null && o2.getReleaseDateInDateType() == null) {
			return 0;
		} else if (o1.getReleaseDateInDateType() != null && o2.getReleaseDateInDateType() == null) {
			return -1;
		} else if (o1.getReleaseDateInDateType() == null && o2.getReleaseDateInDateType() != null) {
			return 1;
		} else
			return o1.getReleaseDateInDateType().compareTo(o2.getReleaseDateInDateType());
	}

}
