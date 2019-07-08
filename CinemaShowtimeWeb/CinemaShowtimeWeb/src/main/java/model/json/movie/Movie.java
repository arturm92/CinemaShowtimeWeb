package model.json.movie;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import model.json.base.BaseModel;

@JsonRootName(value = "movie")
public class Movie extends BaseModel {

	private String slug;
	private String title;
	@JsonProperty("poster_image_thumbnail")
	private String poster;

	@JsonProperty("synopsis")
	private String description;
	@JsonProperty("genres")
	private List<Genre> genre;
	@JsonProperty("poster_image")
	private String posterImages;
	@JsonProperty("scene_images")
	private List<String> sceneImages;
	private List<Trailer> trailers;
	private Ratings ratings;
	private List<Person> cast;
	private List<Person> crew;
	private String website;
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getName() {
		return getTitle();
	}

	public Object getInfo() {
		return getPoster();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPosterImages() {
		return posterImages;
	}

	public void setPosterImages(String posterImages) {
		this.posterImages = posterImages;
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
	
	public String getTrailerURL() {
		return trailers.get(0).getTrailerFiles().get(0).getUrl().replace("watch?v=", "v/");
	}
}
