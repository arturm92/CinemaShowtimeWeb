package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "homePageBean", eager = true)
@SessionScoped
public class HomePageBean {

	private String currentCity;

	private Cinemas cinemas;
	private Movies movies;
	private Movies moviePosters;
	private Showtimes showtimes;
	private Cinema selectedCinema;
	private int distance = 30;
	private String timeTo;

	public HomePageBean() {
		try {
			long startTime = System.currentTimeMillis();

			prepareCinemas();
			customSortCinemas();
			prepareMovies();
			prepareShowtimes();

			long stopTime = System.currentTimeMillis();
			System.out.println("HomePageBean started in " + ((stopTime - startTime) / 1000) + " second");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepareCinemas() {
		Filter filter = prepareCinemaFilter();
		cinemas = ApiHelper.getCinemas(filter);
		if (cinemas.getList().isEmpty()) {
			distance += 30;
			prepareCinemas();
		}
	}

	private void prepareMovies() {
		Filter filter = prepareMoviesInCinema(2);
		filter.deleteFilterParam(Filter.Parameter.LANG);
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);
	}

	private Filter prepareMoviesInCinema(int days) {
		DateFormater df = new DateFormater();
		timeTo = df.convertSimpleDateToTimezone(df.getDaysFromToday(days));
		Filter filter = prepareMovieFilter();
		movies = ApiHelper.getMoviesInCinema(filter);
		if (movies.getList().isEmpty()) {
			days += 5;
			filter = prepareMoviesInCinema(days);
		}
		return filter;
	}

	private void prepareShowtimes() {
		Filter filter = prepareShowtimeFilter();
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
		filter.addFilterParam(Filter.Parameter.DISTANCE, String.valueOf(distance));
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareMovieFilter() {
		Filter filter = new Filter();
		filter.addQueryParam(Filter.Query.CINEMA_ID, getSelectedCinema().getId().toString());
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(Filter.Parameter.TIME_TO, timeTo);
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
	}

	private Filter prepareShowtimeFilter() {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.addQueryParam(Filter.Query.CINEMA_ID, selectedCinema.getId().toString());
		filter.addFilterParam(Filter.Parameter.TIME_FROM, df.convertSimpleDateToTimezone(new Date()));
		filter.addFilterParam(Filter.Parameter.TIME_TO, timeTo);
		return filter;
	}

	public void selectCinema() {
		prepareMovies();
		prepareShowtimes();
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