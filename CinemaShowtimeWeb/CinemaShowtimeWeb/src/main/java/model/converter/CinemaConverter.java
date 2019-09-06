package model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import cinemaShowtime.utils.Application;
import model.json.cinema.Cinema;

@FacesConverter("cinemaConverter")
public class CinemaConverter implements Converter {
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			Cinema cinema = Application.getInstance().getCinemas().findCinemaByName(value);
			return cinema;
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		Cinema cinema = (Cinema) object;
		if (object != null) {
			return cinema.getName();
		} else {
			return null;
		}
	}
}