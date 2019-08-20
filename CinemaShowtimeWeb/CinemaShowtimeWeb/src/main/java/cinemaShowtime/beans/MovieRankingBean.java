package cinemaShowtime.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
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
	private List<Movie> displayRankingList;
	private List<Movie> homePageMovieList;

	private String filterMode;
	private boolean runtimeMovies = true;
	private List<String> filterYearsList;
	private List<String> filteredYearsList;
	private boolean filterChanged = false;

	public MovieRankingBean() {
		long startTime = System.currentTimeMillis();

		initFilterYearsList();
		prepareDisplayRankingList();

		long stopTime = System.currentTimeMillis();
		System.out.println("RankingBean started in " + ((stopTime - startTime) / 1000) + " second");

	}

	private void initFilterYearsList() {
		filterYearsList = new ArrayList<String>();
		int year = 2010;
		for (int i = 0; i < 10; i++) {
			filterYearsList.add(String.valueOf(year));
			year++;
		}
		filteredYearsList = new ArrayList<String>();
		int pos = filterYearsList.size() - 1;
		filteredYearsList.add(filterYearsList.get(pos));
	}

	private void prepareDisplayRankingList() {
		rankingMovies = ApiHelper.getMoviesRanking(runtimeMovies, filteredYearsList);

		moviePosters = ApiHelper.getMoviesPosterEngishVersion(runtimeMovies, filteredYearsList);
		moviePosters.fillMovieMap();
		addPosterToMovie();

		rankingMovies.setList(rankingMovies.getMoviesWithPosterList());
		Collections.sort(rankingMovies.getList(), Collections.reverseOrder());
		addNumberToEachMovie();

		displayRankingList = rankingMovies.getList();
		filterChanged = false;
		
		homePageMovieList = new ArrayList<Movie>();
		homePageMovieList.addAll(displayRankingList.subList(0, 3));
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

	private void addNumberToEachMovie() {
		int number = 1;
		for (Movie movie : rankingMovies.getList()) {
			movie.setNumberInList(number);
			number++;
		}
	}

	private void addPosterToMovie() {
		System.out.println("*********");
		for (Movie movie : rankingMovies.getList()) {
			if (movie.getPosterImage() == null || movie.getPosterImage().equals(Consts.DEFAULT_POSTER)) {
				System.out.println(movie.getTitle() + " | " + movie.getId());
				String moviePoster = moviePosters.getMovieMap().get(movie.getId()).getPosterImage();
				if (moviePoster == null) {
					moviePoster = Consts.DEFAULT_POSTER;
				}
				movie.setPosterImages(moviePoster);
			}
		}
		System.out.println("*********");
	}

	public void filterRankingList() {
		if (filterChanged && chcekFilterParameter()) {
			prepareDisplayRankingList();
		}
		if (filterMode != null || !filterMode.isEmpty()) {
			if (filterMode.equals("TOP50")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(50));
			} else if (filterMode.equals("TOP100")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(100));
			} else if (filterMode.equals("TOP200")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(200));
			}
		} else {
			displayRankingList = rankingMovies.getList().subList(0, getMax(50));
		}
	}

	private boolean chcekFilterParameter() {
		int val = intVal(filteredYearsList.get(0));
		if (val < 2016) {
			if (filteredYearsList.size() > 2) {
				for (String year : filteredYearsList) {
					int nextVal = intVal(year);
					if (nextVal - val > 1) {
						showMessage("Brakuje ciągłości w wybranych latach");
						return false;
					}
					val = nextVal;
				}
				showMessage("Spróbuj zmniejszyć zakres lat");
				return false;
			}
		}
		return true;
	}

	private void showMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd parametrów filtra!", msg));
	}

	private int intVal(String val) {
		return new BigDecimal(val).intValue();
	}

	private int getMax(int mode) {
		int size = rankingMovies.getList().size();
		if (size < mode) {
			return size;
		} else {
			return mode;
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

	public List<Movie> getDisplayRankingList() {
		return displayRankingList;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public List<String> getFilterModeList() {
		return Arrays.asList("TOP50", "TOP100", "TOP200");
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

	public List<String> getFilterYearsList() {
		return filterYearsList;
	}

	public void updateFilterFlag(boolean flag) {
		this.filterChanged = flag;
	}

	public List<String> getFilteredYearsList() {
		return filteredYearsList;
	}

	public void setFilteredYearsList(List<String> filteredYearsList) {
		if (this.filteredYearsList.size() == filteredYearsList.size()) {
			for (int i = 0; i < filteredYearsList.size(); i++) {
				if (this.filteredYearsList.get(i) == filteredYearsList.get(i)) {
					continue;
				} else {
					updateFilterFlag(true);
				}
			}
		} else {
			updateFilterFlag(true);
		}
		this.filteredYearsList = filteredYearsList;
	}

	public boolean isRuntimeMovies() {
		return runtimeMovies;
	}

	public void setRuntimeMovies(boolean runtimeMovies) {
		if (this.runtimeMovies != runtimeMovies) {
			updateFilterFlag(true);
		}
		this.runtimeMovies = runtimeMovies;
	}

	public List<Movie> getHomePageMovieList() {
		
		return homePageMovieList;
	}

}
