package model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
	/*
	 * { "meta_info":{"range_from":0,"range_to":4459,"total_count":4460},
	 * "movies":[{"id":"47080", "slug":"toy-story-4", "title":"Toy Story 4",
	 * "poster_image_thumbnail":
	 * "http://image.tmdb.org/t/p/w154/ehYkgIEVkJY0SBX8cGHjmKtCeri.jpg" }
	 */

	private Long id;
	private String slug;
	private String title;
	@JsonProperty("poster_image_thumbnail")
	private String poster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}
