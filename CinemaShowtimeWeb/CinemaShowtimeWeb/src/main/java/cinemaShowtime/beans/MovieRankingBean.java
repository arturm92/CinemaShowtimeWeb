package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import cinemaShowtime.ApiHelper;
import model.json.complex.Movies;
import model.json.movie.Movie;
import util.Consts;

public class MovieRankingBean {

	private Movie selectedMovie;
	private Movies rankingMovies;
	private Movies moviePosters;
	private List<Movie> rankingList;
	
	public MovieRankingBean() {
		long startTime = System.currentTimeMillis();
		
		rankingMovies = ApiHelper.getMoviesRanking();
		
		moviePosters = ApiHelper.getMoviesPosterEngishVersion();
		moviePosters.fillMovieMap();
		addPosterToMovie();
		
		rankingList = rankingMovies.getMoviesWithPosterList();
		Collections.sort(rankingList, Collections.reverseOrder());
		
		long stopTime = System.currentTimeMillis();
		System.out.println("RankingBean started in " + ((stopTime - startTime) / 1000) + " second");

	}
	
	private void addPosterToMovie() {
		for (Movie movie : rankingMovies.getList()) {
			if (movie.getPosterImage() == null || movie.getPosterImage().equals(Consts.DEFAULT_POSTER)) {
				String moviePoster = moviePosters.getMovieMap().get(movie.getId()).getPosterImage();
				if (moviePoster == null) {
					moviePoster =  Consts.DEFAULT_POSTER;
				}
				movie.setPosterImages(moviePoster);
			}
		}
		
	}

	private void printMoviesRating(List<Movie> movieList) {
		System.out.println("***************");
		for (Movie movie : movieList) {
			System.out.println(movie.getTitle() + " " + movie.getRatingValue());
		}
		System.out.println("***************");
	}

	
	private void printMoives(List<Movie> movieList) {
		System.out.println("***************");
		for (Movie movie : movieList) {
			System.out.println(movie.getId() + " | " + movie.getTitle() + " | " + movie.getPosterImage());
		}
		System.out.println("***************");
	}

	public void select(SelectEvent selectEvent) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			MovieDetailBean.getInstance().initMovieDetailBean(selectedMovie.getId().toString());
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Movie> getRankingList() {
		return rankingList;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

}
