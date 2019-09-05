package model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import cinemaShowtime.utils.Application;
import model.json.movie.Genre;

@FacesConverter("genreConverter")
public class GenreConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			for (Genre genre : Application.getInstance().getGenreList()) {
				if (genre.getName().equals(value)) {
					return genre;
				}
			}
		}
		return null;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		Genre genre = (Genre) object;
		if (object != null) {
			return genre.getName();
		} else {
			return null;
		}
	}
}