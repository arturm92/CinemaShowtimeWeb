package cinemaShowtime;

import java.math.BigDecimal;
import java.util.List;

import model.json.movie.Movie;

public class MovieHelper {
	
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
}
