package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;
import util.Consts;

public class MovieCatalogueBean {

	private List<Movie> movieList;
	private List<Genre> genres;
	private List<String> selectedGenreIds;
	private Date releaseDate;
	private Movies moviePosters;

	public MovieCatalogueBean() {
		movieList = ApiHelper.getMoviesCatalogue().getList();
		moviePosters = ApiHelper.getMoviesPoster();
		moviePosters.fillMovieMap();
		addPosterToMovie();
		genres = ApiHelper.getGenres().removeNullGenres().getList();
	}

	private void addPosterToMovie() {
		for (Movie movie : movieList) {
			if (movie.getPosterImage() == null) {
				String moviePoster = moviePosters.getMovieMap().get(movie.getId()).getPosterImage();
				if (moviePoster == null) {
					moviePoster =  Consts.DEFAULT_POSTER;
				}
				movie.setPosterImages(moviePoster);
			}
		}
		
	}
	
	public void filter() {
		movieList = ApiHelper.getMoviesCatalogueByGenre(selectedGenreIds).getList();
		addPosterToMovie();
		System.out.println("Filtruje");
	}
	
	public void clickMovie() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movies/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public List<Movie> getMovieList() {
		return movieList;
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
