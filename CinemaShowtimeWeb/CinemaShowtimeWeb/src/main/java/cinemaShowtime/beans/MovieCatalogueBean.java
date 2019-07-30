package cinemaShowtime.beans;

import java.util.Date;
import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.complex.Movies;
import model.json.movie.Movie;
import util.Consts;

public class MovieCatalogueBean {

	private List<Movie> movieList;
	private List<String> genres;
	private Date releaseDate;

	public Date getReleaseDate() {
		return releaseDate;
	}

	public MovieCatalogueBean() {
		movieList = ApiHelper.getMoviesCatalogue().getList();
		addPosterToMovie();
	}

	private void addPosterToMovie() {
		Movies moviePosters = ApiHelper.getMoviesPoster();
		moviePosters.fillMovieMap();
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

	public List<Movie> getMovieList() {
		return movieList;
	}

	public List<String> getGenres() {
		return genres;
	}

}
