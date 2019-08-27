package cinemaShowtime.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.LocationApiHelper;
import cinemaShowtime.MovieHelper;
import model.json.cinema.Cinema;
import model.json.cinema.LocationApi;
import model.json.cinema.comparator.CinemaNameComparator;
import model.json.complex.Cinemas;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import util.Consts;

public class HomePageBean {

	private String currentCity;

	private Cinemas cinemas;
	private Movies movies;
	private Movies moviePosters;

	public HomePageBean() {
		try {
			Filter filter = prepareCinemaFilter();
			cinemas = ApiHelper.getCinemas(filter);
			customSortCinemas();
			
			filter = prepareMovieFilter();
			movies = ApiHelper.getMoviesInCinema(filter);
			
			filter.deleteFilterParam(Filter.Parameter.LANG);
			filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
			moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
			moviePosters.fillMovieMap();
			MovieHelper.addPosterToMovie(movies, moviePosters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void customSortCinemas() {
		List<Cinema> premiumCinemas = new ArrayList<Cinema>();
		List<Cinema> others = new ArrayList<Cinema>();
		for (Cinema cinema : cinemas.getList()) {
			if (cinema.getName().contains("Multikino") || cinema.getName().contains("Cinema City")) {
				premiumCinemas.add(cinema);
			}else {
				others.add(cinema);
			}
		}
		Collections.sort(premiumCinemas, new CinemaNameComparator());
		Collections.sort(others, new CinemaNameComparator());
		premiumCinemas.addAll(others);
		cinemas.setList(premiumCinemas);
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
		filter.addQueryParam(Filter.Query.CINEMA_ID, cinemas.getList().get(0).getId().toString());
		filter.setFields(Filter.Field.MOVIE_STANDARD_FIELDS);
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		return filter;
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
	
	public List<Cinema> getCinemaList(){
		return cinemas.getList();
	}
	
	public List<MovieFormatted> getMovieList(){
		return movies.getList();
	}

	public Movies getMovies() {
		return movies;
	}
}
