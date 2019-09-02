package cinemaShowtime.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerBean", eager = true)
@SessionScoped
public class ControllerBean {

	public MovieDetailBean getMovieDetailBean() {
		return MovieDetailBean.getInstance();
	}
	
}
