
package model.json.movie;

import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import model.json.base.BaseModel;

@JsonRootName(value = "movie")
public class Movie extends BaseModel {

	private int numberInList;
	private String slug;
	private String title;
	@JsonProperty("original_title")
	private String originalTitle;
	@JsonProperty("synopsis")
	private String description;
	@JsonProperty("genres")
	private List<Genre> genre;
	@JsonProperty("poster_image")
	private String posterImage;
	@JsonProperty("scene_images")
	private List<String> sceneImages;
	private List<Trailer> trailers;
	private Ratings ratings;
	private List<Person> cast;
	private List<Person> crew;
	private String website;
	@JsonProperty("release_dates")
	private LinkedHashMap<String, Object> releaseDate;
	@JsonProperty("age_limits")
	private LinkedHashMap<String, Object> ageLimit;
	@JsonProperty("original_language")
	private String originalLanguage;

	public int getNumberInList() {
		return numberInList;
	}

	public void setNumberInList(int numberInList) {
		this.numberInList = numberInList;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		if (title != null) {
			return title.toUpperCase();
		} else {
			if (originalTitle != null) {
				return originalTitle.toUpperCase();
			}
		}
		return null;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		/*
		 * if (description == null || description.isEmpty()) { return null; }
		 */
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPosterImage() {
		/*
		 * if (posterImage == null) { return Consts.DEFAULT_POSTER; }
		 */
		return posterImage;
	}

	public void setPosterImages(String posterImage) {
		this.posterImage = posterImage;
	}

	public List<String> getSceneImages() {
		return sceneImages;
	}

	public void setSceneImages(List<String> sceneImages) {
		this.sceneImages = sceneImages;
	}

	public Ratings getRatings() {
		return ratings;
	}

	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}

	public List<Person> getCast() {
		return cast;
	}

	public void setCast(List<Person> cast) {
		this.cast = cast;
	}

	public List<Person> getCrew() {
		return crew;
	}

	public void setCrew(List<Person> crew) {
		this.crew = crew;
	}

	public List<Genre> getGenre() {
		return genre;
	}

	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}

	public List<Trailer> getTrailers() {
		return trailers;
	}

	public void setTrailers(List<Trailer> trailers) {
		this.trailers = trailers;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOriginalTitle() {
		if (originalTitle != null) {
			return originalTitle.toUpperCase();
		}
		return null;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public LinkedHashMap<String, Object> getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LinkedHashMap<String, Object> releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LinkedHashMap<String, Object> getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(LinkedHashMap<String, Object> ageLimit) {
		this.ageLimit = ageLimit;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

}
