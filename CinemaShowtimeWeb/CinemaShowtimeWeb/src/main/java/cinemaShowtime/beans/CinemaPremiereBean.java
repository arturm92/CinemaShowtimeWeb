package cinemaShowtime.beans;

import java.io.IOException;
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

	private Movies movies;
	private Movies moviePosters;
	private String filterMode;

	private DateFormater df = new DateFormater();
	private Date dateFrom;
	
	public CinemaPremiereBean() {
		long startTime = System.currentTimeMillis();
		
		dateFrom = df.getMonthFromToday(0);
		prepareCinemaPremiereMoviesList();

		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPremiereBean started in " + ((stopTime - startTime) / 1000) + " second");
	}

	private void prepareCinemaPremiereMoviesList() {
		Filter filter = prepareFilter();
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMovies(filter);
		MovieHelper.verifyList(movies, dateFrom);
		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

		Collections.sort(movies.getList(), new MovieReleaseDateComparartor());
		//setCinemaPremiereMoviesList(movies.getMoviesWithPosterList());
	}


	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addFilterParam(Filter.Parameter.RELEASE_DATE_FROM, df.formatDateShort(dateFrom));
		filter.addFilterParam(Filter.Parameter.RELEASE_DATE_TO, df.formatDateShort(df.getMonthFromToday(1)));
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

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}

}
