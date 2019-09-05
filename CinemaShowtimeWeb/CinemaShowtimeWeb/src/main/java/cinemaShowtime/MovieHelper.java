package cinemaShowtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.json.complex.Movies;
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

public class MovieHelper {

	public static void verifyList(Movies movies, Date dateFrom) {
		DateFormater df = new DateFormater();
		List<MovieFormatted> veryfiedList = new ArrayList<MovieFormatted>();
		LinkedHashMap<String, Object> releaseDateMap;

		for (MovieFormatted movie : movies.getList()) {
			boolean addMovie = true;
			// System.out.println(movie.getTitle());
			if (Util.containsSecialCharacter(movie.getTitle())) { // rozpoznaje dziwne znaki w tytułach
				addMovie = false;
				// System.out.println("japonskie albo ruskie gówno");
			} else if (movie.getGenre() == null || movie.getGenre().isEmpty()) { // odrzuca braki w opisie gatunków
				addMovie = false;
				// System.out.println("brak gatunku");
			} else {
				releaseDateMap = movie.getReleaseDate();
				if (releaseDateMap != null) { // tylko światowe filmy
					if (releaseDateMap.size() <= 2 && !releaseDateMap.containsKey("PL")) {
						addMovie = false;
						// System.out.println("kino niszowe");
					} else { // sprawdzanie daty wydanaia (opcjonalne)s
						if (dateFrom != null) {
							for (Map.Entry entry : releaseDateMap.entrySet()) {
								List<Object> list = (List<Object>) entry.getValue();
								LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(0);
								Date releaseDate = df.parseString(map.get("date"));
								if (releaseDate.compareTo(dateFrom) < 0) {
									addMovie = false;
								} /*
									 * else { System.out.println(entry.getKey());
									 * System.out.println(map.get("date")); }
									 */
							}
						}
					}
				}
			}
			if (addMovie) {
				veryfiedList.add(movie);
			}
		}
		movies.setList(veryfiedList);
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
			movie.setDescription(movieDescripstion.getDescription());
			movie.setTitle(movieDescripstion.getTitle());
			movie.setOriginalTitle(movieDescripstion.getOriginalTitle());
			movie.setGenre(movieDescripstion.getGenre());
			movie.setCast(movieDescripstion.getCast());
			movie.setCrew(movieDescripstion.getCrew());
			movie.setWebsite(movieDescripstion.getWebsite());
			movie.setReleaseDate(movieDescripstion.getReleaseDate());
			movie.setAgeLimit(movieDescripstion.getAgeLimit());
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
