package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.Movie;
import util.Consts;
import util.DateFormater;

public class CinemaPremiereBean {

	private List<Movie> cinemaPremiereMoviesList;
	private Movies cinemaPremiereMovies;
	private Movies moviePosters;
	private String filterMode;

	public CinemaPremiereBean() {
		long startTime = System.currentTimeMillis();

		prepareCinemaPremiereMoviesList();

		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPreviewBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void prepareCinemaPremiereMoviesList() {
		Filter filter = prepareFilter();
		cinemaPremiereMovies = ApiHelper.getCinemaPreviewMovies(filter);

		filter.deleteParam(Filter.LANG);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(cinemaPremiereMovies, moviePosters);

		Collections.sort(cinemaPremiereMovies.getList(), new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				return o1.getReleaseDateInDateType().compareTo(o2.getReleaseDateInDateType());
			}
		});

		verifyList();
		setCinemaPremiereMoviesList(cinemaPremiereMovies.getMoviesWithPosterList());
	}

	private void verifyList() {
		DateFormater df = new DateFormater();
		Date dateFrom = df.parseString(df.recalculateDateByMonth(-1));
		List<Movie> veryfiedList = new ArrayList<Movie>();
		for (Movie movie : cinemaPremiereMovies.getList()) {
			System.out.println(movie.getTitle() + " " + movie.getOriginalTitle());
			if (movie.getReleaseDateInDateType().compareTo(dateFrom) > 0) {
				veryfiedList.add(movie);
			}
		}
		cinemaPremiereMovies.setList(veryfiedList);
	}

	private Filter prepareFilter() {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.addParam(Filter.RELEASE_DATE_FROM, df.recalculateDateByMonth(-1));
		filter.addParam(Filter.RELEASE_DATE_TO, df.recalculateDateByMonth(0));
		filter.addParam(Filter.LANG, Consts.LANGUAGE);
		filter.addParam(Filter.COUNTRIES, Consts.COUNTRIES + ",US");
		return filter;
	}

	public void filter() {
		prepareCinemaPremiereMoviesList();
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

	public List<String> getFilterModeList() {
		return Arrays.asList("dupa", "dupa2");
	}

	public String getFilterMode() {
		if (filterMode == null) {
			return getFilterModeList().get(0);
		}
		return filterMode;
	}

	public void setFilterMode(String filterMode) {
		this.filterMode = filterMode;
	}

	public List<Movie> getCinemaPremiereMoviesList() {
		return cinemaPremiereMoviesList;
	}

	public void setCinemaPremiereMoviesList(List<Movie> cinemaPremiereMoviesList) {
		this.cinemaPremiereMoviesList = cinemaPremiereMoviesList;
	}
}
