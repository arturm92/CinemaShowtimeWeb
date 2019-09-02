package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import model.json.movie.comparator.MovieReleaseDateComparartor;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "cinemaPremiereBean", eager = true)
@SessionScoped
public class CinemaPremiereBean {

	private List<MovieFormatted> cinemaPremiereMoviesList;
	private Movies cinemaPremiereMovies;
	private Movies moviePosters;
	private String filterMode;

	public CinemaPremiereBean() {
		long startTime = System.currentTimeMillis();

		prepareCinemaPremiereMoviesList();

		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPremiereBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void prepareCinemaPremiereMoviesList() {
		Filter filter = prepareFilter();
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		cinemaPremiereMovies = ApiHelper.getMovies(filter);

		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(cinemaPremiereMovies, moviePosters);

		Collections.sort(cinemaPremiereMovies.getList(), new MovieReleaseDateComparartor());

		verifyList();
		setCinemaPremiereMoviesList(cinemaPremiereMovies.getMoviesWithPosterList());
	}

	private void verifyList() {
		DateFormater df = new DateFormater();
		Date dateFrom = df.parseString(df.recalculateDateByMonth(-1));
		List<MovieFormatted> veryfiedList = new ArrayList<MovieFormatted>();
		for (MovieFormatted movie : cinemaPremiereMovies.getList()) {
			if (movie.getReleaseDateInDateType().compareTo(dateFrom) > 0) {
				veryfiedList.add(movie);
			}
		}
		cinemaPremiereMovies.setList(veryfiedList);
	}

	private Filter prepareFilter() {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.addFilterParam(Filter.Parameter.RELEASE_DATE_FROM, df.recalculateDateByMonth(-1));
		filter.addFilterParam(Filter.Parameter.RELEASE_DATE_TO, df.recalculateDateByMonth(0));
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(Filter.Parameter.COUNTRIES, Consts.COUNTRIES + ",US");
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

	public List<MovieFormatted> getCinemaPremiereMoviesList() {
		return cinemaPremiereMoviesList;
	}

	public void setCinemaPremiereMoviesList(List<MovieFormatted> cinemaPremiereMoviesList) {
		this.cinemaPremiereMoviesList = cinemaPremiereMoviesList;
	}
}
