package model.json.movie;

import model.json.base.BaseModel;

public class Genre extends BaseModel implements Comparable<Genre>{

	@Override
	public int compareTo(Genre genre) {
		return this.getName().compareTo(genre.getName());
	}

}
