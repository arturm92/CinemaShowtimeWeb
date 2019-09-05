package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.MovieFilter;
import cinemaShowtime.ApiFilter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import model.json.movie.comparator.MovieTitleComparator;
import util.Consts;

@ManagedBean(name = "movieCatalogueBean", eager = true)
@SessionScoped
public class MovieCatalogueBean {

	private Movies movies;
	private Movies moviePosters;
	private MovieFilter movieFilter;

	public MovieCatalogueBean() {
		long startTime = System.currentTimeMillis();

		movieFilter = new MovieFilter();
		prepareMovieCatalogueList();

		long stopTime = System.currentTimeMillis();
		System.out.println("MovieCatalogueBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void prepareMovieCatalogueList() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMoviesCatalogue(filter);
		MovieHelper.verifyList(movies, null);
		Collections.sort(movies.getList(), new MovieTitleComparator());

		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
		
		movieFilter.updateFilterFlag(false);
	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Consts.COUNTRIES);
		if (!movieFilter.getFilteredYearList().isEmpty()) {
			String dateFrom = MovieHelper.getMinYear(movieFilter.getFilteredYearList()) + "-01-01";
			String dateTo = MovieHelper.getMaxYear(movieFilter.getFilteredYearList()) + "-12-31";
			filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, dateFrom);
			filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_TO, dateTo);
		}
		if (!movieFilter.getSelectedGenreIds().isEmpty()) {
			String genre_ids = "";
			for (String id : movieFilter.getSelectedGenreIds()) {
				genre_ids += id + ",";
			}
			filter.addFilterParam(ApiFilter.Parameter.GENRE_IDS, genre_ids);
		}
		return filter;
	}

	public void filter() {
		if (movieFilter.canFilter()) {
			prepareMovieCatalogueList();
		}
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

	public MovieFilter getMovieFilter() {
		return movieFilter;
	}
}
