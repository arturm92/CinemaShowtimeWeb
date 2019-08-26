package model.json.movie.comparator;

import java.util.Comparator;

import model.json.movie.MovieFormatted;

public class MovieRatingComparator  implements Comparator<MovieFormatted> {

	@Override
	public int compare(MovieFormatted o1, MovieFormatted o2) {
		return o1.getRatingValue().compareTo(o2.getRatingValue());
	}

}
