package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.LocationApiHelper;
import cinemaShowtime.MovieHelper;
import model.json.Showtime;
import model.json.cinema.Cinema;
import model.json.cinema.LocationApi;
import model.json.cinema.comparator.CinemaNameComparator;
import model.json.complex.Cinemas;
import model.json.complex.Movies;
import model.json.complex.Showtimes;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

public class HomePageBean {

	private String currentCity;

	private Cinemas cinemas;
	private Movies movies;
	private Movies moviePosters;
	private Showtimes showtimes;

	private Cinema selectedCinema;

	public HomePageBean() {
		try {
			long startTime = System.currentTimeMillis();

			prepareCinemas();
			prepareMoviesInCinema();

			long stopTime = System.currentTimeMillis();
			System.out.println("HomePageBean started in " + ((stopTime - startTime) / 1000) + " second");

			prepareShowtimesInCinema();

			long stopTime2 = System.currentTimeMillis();
			System.out.println("Showtimes load in " + ((stopTime2 - stopTime) / 1000) + " second");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepareCinemas() {
		Filter filter = prepareCinemaFilter();
		cinemas = ApiHelper.getCinemas(filter);
		customSortCinemas();
	}

	private void prepareMoviesInCinema() {
		Filter filter;
		filter = prepareMovieFilter();
		movies = ApiHelper.getMoviesInCinema(filter);

		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	private void prepareShowtimesInCinema() {
		Filter filter = prepareShowtimeFilter(null);
		showtimes = ApiHelper.getMovieShowtimesInCinema(filter);
		for (MovieFormatted movie : movies.getList()) {
			List<Showtime> movieShowtime = showtimes.findMovieShowtime(movie.getId());
			Showtimes tmp = new Showtimes();
			tmp.setList(movieShowtime);
			movie.setShowtimeDayList(tmp.getNormalizeList());
		}
	}

	private Filter prepareCinemaFilter() {
		LocationApi locationApi = LocationApiHelper.getLocation();
		setCurrentCity(locationApi.getCity());

		Filter filter = new Filter();
		filter.addFilterParam(Filter.Parameter.LOCATION, locationApi.getLatitude() + "," + locationApi.getLongitude());
		filter.addFilterParam(Filter.Parameter.DISTANCE, "30");
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareMovieFilter() {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.addQueryParam(Filter.Query.CINEMA_ID, getSelectedCinema().getId().toString());
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(Filter.Parameter.TIME_TO, df.convertSimpleDateToTimezone(df.getDaysFromToday(2)));
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareShowtimeFilter(Movie movie) {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.addQueryParam(Filter.Query.CINEMA_ID, selectedCinema.getId().toString());
		filter.addFilterParam(Filter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(Filter.Parameter.TIME_TO, df.convertSimpleDateToTimezone(df.getDaysFromToday(2)));
		return filter;
	}

	public void selectCinema() {
		prepareMoviesInCinema();
		prepareShowtimesInCinema();
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

	private void customSortCinemas() {
		List<Cinema> premiumCinemas = new ArrayList<Cinema>();
		List<Cinema> others = new ArrayList<Cinema>();
		for (Cinema cinema : cinemas.getList()) {
			if (cinema.getName().contains("Multikino") || cinema.getName().contains("Cinema City")) {
				premiumCinemas.add(cinema);
			} else {
				others.add(cinema);
			}
		}
		Collections.sort(premiumCinemas, new CinemaNameComparator());
		Collections.sort(others, new CinemaNameComparator());
		premiumCinemas.addAll(others);
		cinemas.setList(premiumCinemas);
	}

	public Cinemas getCinemas() {
		return cinemas;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public List<Cinema> getCinemaList() {
		return cinemas.getList();
	}

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}

	public Movies getMovies() {
		return movies;
	}

	public Cinema getSelectedCinema() {
		if (selectedCinema == null) {
			selectedCinema = cinemas.getList().get(0);
		}
		return selectedCinema;
	}

	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

}