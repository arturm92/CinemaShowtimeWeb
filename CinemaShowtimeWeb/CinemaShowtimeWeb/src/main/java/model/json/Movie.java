package model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.base.BaseModel;

public class Movie extends BaseModel{
	/*
	 * { "meta_info":{"range_from":0,"range_to":4459,"total_count":4460},
	 * "movies":[{"id":"47080", "slug":"toy-story-4", "title":"Toy Story 4",
	 * "poster_image_thumbnail":
	 * "http://image.tmdb.org/t/p/w154/ehYkgIEVkJY0SBX8cGHjmKtCeri.jpg" }
	 */

	private String slug;
	private String title;
	@JsonProperty("poster_image_thumbnail")
	private String poster;

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
}
