package cinemaShowtime.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import util.Consts;
import util.Utils;

@ManagedBean(name = "controllerBean", eager = true)
@SessionScoped
public class ControllerBean {

	private String endpoint;

	private CityBean cityBean;

	public ControllerBean() {
		cityBean = new CityBean();
		System.out.println("ControllerBean started!");
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

	public String getMoviesInCinemaTable() {
		// String json = api.getAllMoviesInCinema(param);
		return null;

	}

	public Object getList() {
		return Utils.getInstance().getList();
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

}
