package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;
import model.json.movie.comparator.MovieRatingComparator;
import model.json.movie.comparator.MovieReleaseDateComparartor;
import model.json.movie.comparator.MovieTitleComparator;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "nowShowingMovieBean", eager = true)
@SessionScoped
public class NowShowingMovieBean {

	private Movies movies;
	private Movies moviePosters;
	private Movie selectedMovie;
	private boolean sortType;

	public NowShowingMovieBean() {
		long startTime = System.currentTimeMillis();

		prepareMovies();
		filterMovies();
		Collections.sort(movies.getList(), Collections.reverseOrder(new MovieReleaseDateComparartor()));

		long stopTime = System.currentTimeMillis();
		System.out.println("NowShowingMovieBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void prepareMovies() {
		Filter filter = prepareFilter();
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMovies(filter);
		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	private void filterMovies() {
		List<MovieFormatted> list = new ArrayList<MovieFormatted>();
		for (MovieFormatted movie : movies.getList()) {
			if (movie.getReleaseDate() != null
					&& (movie.getReleaseDate().containsKey("PL") || movie.getReleaseDate().containsKey("US")
							&& !(movie.getGenre() == null || movie.getGenre().isEmpty()))) {
				list.add(movie);
			}
		}
		movies.setList(list);
	}

	public void clickMovie() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Filter prepareFilter() {
		DateFormater df = new DateFormater();
		Filter filter = new Filter();
		String date = df.convertSimpleDateToTimezone(df.getDaysFromToday(0));
		filter.addFilterParam(Filter.Parameter.TIME_FROM, date);
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(Filter.Parameter.COUNTRIES, Consts.COUNTRIES);
		return filter;
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

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public boolean isSortType() {
		return sortType;
	}

	public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}
}
