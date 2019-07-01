package cinemaShowtime.beans;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import cinemaShowtime.ApiHelper;
import model.json.City;
import util.Consts;
import util.Utils;

@ManagedBean(name = "controllerBean", eager = true)
@ApplicationScoped
public class ControllerBean {

	private String endpoint;
	public Utils utils;
	private String selected;
	private City selectedCity;

	private CinemaBean cinemaBean;

	public ControllerBean() {
		utils = new Utils();
		utils.setCurrentEndpoint(Consts.CITIES);
		System.out.println("ControllerBean started!");
	}

	public String getMessage() {
		return "Hello World!";
	}

	public List<String> getEndpointList() {
		return Consts.ENDPOINTS;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getNextPage() {
		String json = ApiHelper.getDataFromApi(Consts.CITIES);
		utils.listValues(json);
		return "result.xhtml";

	}

	public String getMoviesInCinemaTable() {
		String param = getSelected();
		// String json = api.getAllMoviesInCinema(param);
		return null;

	}

	public Object getList() {
		return utils.getList();
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public City getSelectedCity() {
		City city = utils.getCities().findCity(getSelected());
		setSelectedCity(city);
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
	}

	public String showCinemasInCity() {
		cinemaBean = new CinemaBean(selectedCity);
		return null;
	}

	public CinemaBean getCinemaBean() {
		return cinemaBean;
	}

	public void setCinemaBean(CinemaBean cinemaBean) {
		this.cinemaBean = cinemaBean;
	}
}
