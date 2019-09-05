package cinemaShowtime.filters;

import java.util.Collections;

import model.json.complex.Movies;
import model.json.movie.comparator.MovieRatingComparator;
import model.json.movie.comparator.MovieReleaseDateComparartor;
import model.json.movie.comparator.MovieTitleComparator;

public class MovieSorter {

	private boolean sortType = false;
	private Movies movies;

	public MovieSorter() {
	}

	public boolean isSortType() {
		return sortType;
	}

	public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}

	public Movies getMovies() {
		return movies;
	}

	public void setMovies(Movies movies) {
		this.movies = movies;
	}

	public void dateSort() {
		if (sortType) {
			Collections.sort(movies.getList(), new MovieReleaseDateComparartor());
		} else {
			Collections.sort(movies.getList(), Collections.reverseOrder(new MovieReleaseDateComparartor()));
		}
	}

	public void titleSort() {
		if (sortType) {
			Collections.sort(movies.getList(), new MovieTitleComparator());
		} else {
			Collections.sort(movies.getList(), Collections.reverseOrder(new MovieTitleComparator()));
		}
	}

	public void ratingSort() {
		if (sortType) {
			Collections.sort(movies.getList(), new MovieRatingComparator());
		} else {
			Collections.sort(movies.getList(), Collections.reverseOrder(new MovieRatingComparator()));
		}
	}
}
