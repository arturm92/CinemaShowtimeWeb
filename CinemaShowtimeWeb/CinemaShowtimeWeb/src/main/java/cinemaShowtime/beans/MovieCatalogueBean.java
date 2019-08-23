package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;
import util.Consts;
import util.DateFormater;

public class MovieCatalogueBean {

	private Movies movies;
	private List<Genre> genres;
	private List<String> selectedGenreIds;
	private Date releaseDate;
	private Movies moviePosters;

	public MovieCatalogueBean() {
		genres = ApiHelper.getGenres().removeNullGenres().getList();
		selectedGenreIds = new ArrayList<String>();
		prepareMovieCatalogueList();
	}

	private void prepareMovieCatalogueList() {
		Filter filter = prepareFilter();
		movies = ApiHelper.getMoviesCatalogue(filter);
		filter.deleteParam(Filter.LANG);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	private Filter prepareFilter() {
		Filter filter = new Filter();
		filter.addParam(Filter.LANG, Consts.LANGUAGE);
		filter.addParam(Filter.COUNTRIES, Consts.COUNTRIES);
		if (releaseDate != null) {
			DateFormater df = new DateFormater();
			String date = df.formatDateShort(releaseDate);
			filter.addParam(Filter.RELEASE_DATE_FROM,date );
		}
		if (!selectedGenreIds.isEmpty()) {
			String genre_ids = "";
			for (String id : selectedGenreIds) {
				genre_ids += id + ",";
			}
			filter.addParam(Filter.GENRE_IDS,genre_ids );
		}
		return filter;
	}

	public void filter() {
		prepareMovieCatalogueList();
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
	
	public List<Movie> getMovieList() {
		return movies.getList();
	}

	public List<Genre> getGenres() {
		return genres;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<String> getSelectedGenreIds() {
		return selectedGenreIds;
	}

	public void setSelectedGenreIds(List<String> selectedGenreIds) {
		this.selectedGenreIds = selectedGenreIds;
	}
}
