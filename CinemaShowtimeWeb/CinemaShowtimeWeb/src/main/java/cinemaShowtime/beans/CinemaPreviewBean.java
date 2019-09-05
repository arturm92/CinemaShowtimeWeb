package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.ApiFilter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import model.json.movie.comparator.MovieReleaseDateComparartor;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "cinemaPreviewBean", eager = true)
@SessionScoped
public class CinemaPreviewBean {

	private Movies movies;
	private Movies moviePosters;

	private DateFormater df = new DateFormater();
	private Date dateFrom;

	public CinemaPreviewBean() {
		long startTime = System.currentTimeMillis();

		dateFrom = df.getMonthFromToday(1);
		prepareCinemaPreviewMoviesList();
		Collections.sort(movies.getList(), new MovieReleaseDateComparartor());

		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPreviewBean started in " + ((stopTime - startTime) / 1000) + " second");
	}

	private void prepareCinemaPreviewMoviesList() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);

		movies = ApiHelper.getMovies(filter);
		MovieHelper.verifyList(movies, dateFrom);
		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);

		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.INCLUDE_UPCOMINGS, "true");
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, df.formatDateShort(dateFrom));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Consts.COUNTRIES);
		return filter;
	}

	public void filter() {
		prepareCinemaPreviewMoviesList();
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

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}

	/*
	 * public List<String> getFilterModeList() { return Arrays.asList("POLSKA",
	 * "SWIAT"); }
	 * 
	 * public String getFilterMode() { if (filterMode == null) { return
	 * getFilterModeList().get(0); } return filterMode; }
	 * 
	 * public void setFilterMode(String filterMode) { this.filterMode = filterMode;
	 * }
	 */
}
