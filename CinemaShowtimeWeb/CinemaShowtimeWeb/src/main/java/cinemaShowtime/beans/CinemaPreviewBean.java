package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

public class CinemaPreviewBean {
	
	private List<MovieFormatted> cinemaPreviewMoviesList;
	private Movies cinemaPreviewMovies;
	private Movies moviePosters;
	private String filterMode;
	

	public CinemaPreviewBean() {
		long startTime = System.currentTimeMillis();
		
		prepareCinemaPreviewMoviesList();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPreviewBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void prepareCinemaPreviewMoviesList() {
		Filter filter = prepareFilter();
		cinemaPreviewMovies = ApiHelper.getMovies(filter);
		
		filter.deleteParam(Filter.LANG);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(cinemaPreviewMovies,moviePosters);
		
		cinemaPreviewMoviesList = cinemaPreviewMovies.getList();
	}
	
	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addParam(Filter.INCLUDE_UPCOMINGS, "true");
		DateFormater df = new DateFormater();
		String date = df.formatDateShort(new Date());
		filter.addParam(Filter.RELEASE_DATE_FROM, date);
		if (getFilterMode().equals("POLSKA")) {
			filter.addParam(Filter.LANG, Consts.LANGUAGE);
			filter.addParam(Filter.COUNTRIES, Consts.COUNTRIES);
		}else {
			filter.addParam(Filter.COUNTRIES, Consts.COUNTRIES + ",US");
		}
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
	
	public List<MovieFormatted> getCinemaPreviewMoviesList() {
		return cinemaPreviewMoviesList;
	}

	public List<String> getFilterModeList() {
		return Arrays.asList("POLSKA", "SWIAT");
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
}
