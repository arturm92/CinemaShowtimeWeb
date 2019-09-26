package model.json.complex;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.json.cinema.Cinema;
import model.json.movie.Genre;

public class Genres {

	@JsonProperty("genres")
	private List<Genre> list;

	public List<Genre> getList() {
		return list;
	}

	public void setList(List<Genre> list) {
		this.list = list;
	}

	public Genres removeNullGenres() {
		List<Genre> filteredList = new ArrayList<Genre>();
		for (Genre genre : list) {
			if (genre.getName() != null) {
				filteredList.add(genre);
			} else {
				break;
			}
		}
		list = filteredList;
		return this;
	}

	public Genre findGenreById(Integer id) {
		for (Genre genre : list) {
			if (genre.getId().compareTo(new Long(id)) == 0) {
				return genre;
			}
		}
		return null;
	}
}
