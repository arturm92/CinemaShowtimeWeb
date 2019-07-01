package cinemaShowtime.beans;

import java.util.List;

import cinemaShowtime.ApiHelper;
import model.json.Cinema;
import model.json.City;
import util.Consts;
import util.Utils;

public class CinemaBean {

	City city;
	private Utils util;
	public List<Cinema> list;

	public CinemaBean(City city) {
		this.city = city;
		util = new Utils();
		util.setCurrentEndpoint(Consts.CINEMAS);
		initCinemaList();
	}

	private void initCinemaList() {
		String json = ApiHelper.getAllCinemasInCity(city);
		util.listValues(json);
		setList(util.getCinemasList());
	}

	public List<Cinema> getList() {
		return list;
	}

	public void setList(List<Cinema> list) {
		this.list = list;
	}
	
	

}
