package model.json.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ratings {

	@JsonProperty("imdb")
	private Rating imdbRating;
	@JsonProperty("tmdb")
	private Rating tmdbRating;

	public Rating getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Rating imdbRating) {
		this.imdbRating = imdbRating;
	}

	public Rating getTmdbRating() {
		return tmdbRating;
	}

	public void setTmdbRating(Rating tmdbRating) {
		this.tmdbRating = tmdbRating;
	}

}
