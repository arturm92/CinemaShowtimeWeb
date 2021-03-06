package model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import cinemaShowtime.utils.Application;
import model.json.City;

@FacesConverter("cityConverter")
public class CityConverter implements Converter {
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			City city = Application.getInstance().getCities().findCityByName(value);
			return city;
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		City city = (City) object;
		if (object != null) {
			return city.getName();
		} else {
			return null;
		}
	}
}