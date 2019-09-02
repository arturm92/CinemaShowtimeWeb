package cinemaShowtime.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerBean", eager = true)
@SessionScoped
public class ControllerBean {

	private CityBean cityBean;

	public ControllerBean() {
		cityBean = new CityBean();
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public MovieDetailBean getMovieDetailBean() {
		return MovieDetailBean.getInstance();
	}
	
}
