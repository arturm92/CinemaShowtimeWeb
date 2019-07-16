package cinemaShowtime.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import model.json.movie.Movie;

@ManagedBean(name = "controllerBean", eager = true)
@ViewScoped
public class ControllerBean {

	private CityBean cityBean;
	private List<Movie> headerMovies;

	public ControllerBean() {
		cityBean = new CityBean();
		this.headerMovies = ApiHelper.getNewestMovies().getList();
		System.out.println("ControllerBean started!");
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	public List<Movie> getHeaderMovies() {
		return headerMovies;
	}
	
	public void clickHeaderMovie() {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    String movieId = ec.getRequestParameterMap().get("movieId");
		System.out.println("Header movie clicked!" + movieId);
	}

}
