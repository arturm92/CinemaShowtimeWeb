package model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import cinemaShowtime.utils.Application;
import model.json.movie.Movie;

@FacesConverter("movieConverter")
public class MovieConverter implements Converter {
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			Movie movie = Application.getInstance().getMovies().findMovieByTitle(value);
			return movie;
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		Movie movie = (Movie) object;
		if (object != null) {
			return movie.getTitle();
		} else {
			return null;
		}
	}
}