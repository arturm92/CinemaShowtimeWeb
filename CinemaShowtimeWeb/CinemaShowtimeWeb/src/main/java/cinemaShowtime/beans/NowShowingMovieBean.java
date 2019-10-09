package cinemaShowtime.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.filters.Filter;
import cinemaShowtime.filters.FilterInterfaceImpl;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.Const;
import cinemaShowtime.utils.DateFormater;
import cinemaShowtime.utils.Logger;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "nowShowingMovieBean", eager = true)
@ViewScoped
public class NowShowingMovieBean extends FilterInterfaceImpl {

	private Movies movies;
	private Movies moviePosters;
	private Movie selectedMovie;

	public NowShowingMovieBean() {
		long startTime = System.currentTimeMillis();

		setConfiguration(Filter.Configuration.NOW_SHOWING);
		initFilter();
		prepareMovies();

		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	private void prepareMovies() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMovies(filter);
		MovieHelper.verifyList(movies, null, getMovieFilter().getSelectedGenreList());

		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

		getMovieSorter().setMovies(movies);
		getMovieSorter().setSortType(false);
		getMovieSorter().ratingSort();
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

	private ApiFilter prepareFilter() {
		DateFormater df = new DateFormater();
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(df.getDaysFromToday(0)));
		filter.addFilterParam(ApiFilter.Parameter.TIME_TO, df.convertSimpleDateToTimezone(df.getDaysFromToday(1)));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Const.COUNTRIES);
		if (!getMovieFilter().getSelectedGenreList().isEmpty()) {
			String genre_ids = "";
			for (Genre genre : getMovieFilter().getSelectedGenreList()) {
				genre_ids += genre.getId() + ",";
			}
			filter.addFilterParam(ApiFilter.Parameter.GENRE_IDS, genre_ids);
		}
		return filter;
	}

	public void doFilter() {
		if (getMovieFilter().canFilter()) {
			prepareMovies();
		}
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}
}
