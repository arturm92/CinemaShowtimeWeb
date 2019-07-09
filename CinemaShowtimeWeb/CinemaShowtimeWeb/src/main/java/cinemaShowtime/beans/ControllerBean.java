package cinemaShowtime.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "controllerBean", eager = true)
@ViewScoped
public class ControllerBean {

	private CityBean cityBean;
	
	
	public ControllerBean() {
		cityBean = new CityBean();
		System.out.println("ControllerBean started!");
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

}
