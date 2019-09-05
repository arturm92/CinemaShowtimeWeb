package cinemaShowtime.helpers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cinemaShowtime.utils.Consts;
import cinemaShowtime.utils.DateFormater;
import cinemaShowtime.utils.Util;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

public class MovieHelper {

	public static void verifyList(Movies movies, Date dateFrom, List<Genre> genreList) {
		List<MovieFormatted> veryfiedList = new ArrayList<MovieFormatted>();
		LinkedHashMap<String, Object> releaseDateMap;

		for (MovieFormatted movie : movies.getList()) {
			boolean addMovie = true;
			releaseDateMap = movie.getReleaseDate();
			// System.out.println(movie.getTitle());
			if (Util.containsSecialCharacter(movie.getTitle())
					|| Util.containsSecialCharacter(movie.getOriginalTitle())) { // rozpoznaje dziwne znaki w tytułach
				addMovie = false;
			} else if (movie.getGenre() == null || movie.getGenre().isEmpty()) { // odrzuca braki w opisie gatunków
				addMovie = false;
			} else if (releaseDateMap != null) { // tylko światowe filmy
				if (releaseDateMap.size() <= 3 && releaseDateMap.size() > 2 && (releaseDateMap.containsKey("IN")
						|| releaseDateMap.containsKey("AR") || releaseDateMap.containsKey("IT"))) {
					addMovie = false;
				} else { // sprawdzanie daty wydanaia (opcjonalne)
					if (dateFrom != null) {
						addMovie = chcekReleaseDate(dateFrom, releaseDateMap);
					}
				}
			} else {
				addMovie = false;
			}
			if (addMovie) { // sprawdza zgodnosc gatunku (opcjonalne)
				if (checkMovieGenre(genreList, movie)) {
					veryfiedList.add(movie);
				}
			}
		}
		movies.setList(veryfiedList);

	}

	private static boolean chcekReleaseDate(Date dateFrom, LinkedHashMap<String, Object> releaseDateMap) {
		DateFormater df = new DateFormater();
		for (Map.Entry entry : releaseDateMap.entrySet()) {
			List<Object> list = (List<Object>) entry.getValue();
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(0);
			Date releaseDate = df.parseString(map.get("date"));
			if (releaseDate.compareTo(dateFrom) < 0) {
				return false;
			} /*
				 * else { System.out.println(entry.getKey());
				 * System.out.println(map.get("date")); }
				 */
		}
		return true;
	}

	private static boolean checkMovieGenre(List<Genre> genreList, MovieFormatted movie) {
		boolean addMovie;
		addMovie = false;
		if (genreList == null || genreList.isEmpty()) {
			return true;
		} else {
			for (Genre genre : genreList) {
				// System.out.println("filter genre" + genre.getName());
				// System.out.println("movie genres:");
				if (!addMovie) {
					for (Genre movieGenre : movie.getGenre()) {
						System.out.println(movieGenre.getName() + "/" + movieGenre.getId());
						if (movieGenre.getId().compareTo(genre.getId()) == 0) {
							addMovie = true;
							// System.out.println("dobry gatunek");
							break;
						} else {
							addMovie = false;
							// System.out.println("zły gatunek");
						}
					}
				}
			}
		}
		return addMovie;
	}

	public static void addPosterToMovie(Movies movies, Movies moviePosters) {
		for (Movie movie : movies.getList()) {
			if (movie.getPosterImage() == null || movie.getPosterImage().equals(Consts.DEFAULT_POSTER)) {
				try {
					String moviePoster = moviePosters.getMovieMap().get(movie.getId()).getPosterImage();
					if (moviePoster == null) {
						moviePoster = Consts.DEFAULT_POSTER;
					}
					movie.setPosterImages(moviePoster);
				} catch (NullPointerException e) {
					System.out.println(movie.getTitle() + "/" + movie.getId() + "/" + movie.getPosterImage());
				}

			}
		}
	}

	public static void mergeMovieDetails(Movie movie, Movie movieDescripstion) {
		if (movie.getId().compareTo(movieDescripstion.getId()) == 0) {
			if (movieDescripstion.getDescription() != null) {
				movie.setDescription(movieDescripstion.getDescription());
			}
			if (movieDescripstion.getTitle() != null) {
				movie.setTitle(movieDescripstion.getTitle());
			}
			if (movieDescripstion.getOriginalTitle() != null) {
				movie.setOriginalTitle(movieDescripstion.getOriginalTitle());
			}
			movie.setGenre(movieDescripstion.getGenre());
			movie.setCast(movieDescripstion.getCast());
			movie.setCrew(movieDescripstion.getCrew());
			movie.setWebsite(movieDescripstion.getWebsite());
			movie.setReleaseDate(movieDescripstion.getReleaseDate());
			movie.setAgeLimit(movieDescripstion.getAgeLimit());
			if (movie.getPosterImage() == null) {
				movie.setPosterImages(Consts.DEFAULT_POSTER);
			}
		}
	}

	public static BigDecimal getMinYear(List<String> yearsList) {
		BigDecimal min = new BigDecimal(yearsList.get(0));
		for (String year : yearsList) {
			BigDecimal tmp = new BigDecimal(year);
			if (tmp.compareTo(min) < 0) {
				min = tmp;
			}
		}
		return min;
	}

	public static BigDecimal getMaxYear(List<String> yearsList) {
		BigDecimal max = new BigDecimal(yearsList.get(0));
		for (String year : yearsList) {
			BigDecimal tmp = new BigDecimal(year);
			if (tmp.compareTo(max) > 0) {
				max = tmp;
			}
		}
		return max;
	}

	public static void printMoviesRating(List<MovieFormatted> movieList) {
		System.out.println("***************");
		for (MovieFormatted movie : movieList) {
			System.out.println(movie.getTitle() + " " + movie.getRatingValue());
		}
		System.out.println("***************");
	}

	public static void printMoives(List<MovieFormatted> movieList) {
		System.out.println("***************");
		for (MovieFormatted movie : movieList) {
			System.out.println(movie.getId() + " | " + movie.getTitle() + " | " + movie.getPosterImage());
		}
		System.out.println("***************");
	}

}
