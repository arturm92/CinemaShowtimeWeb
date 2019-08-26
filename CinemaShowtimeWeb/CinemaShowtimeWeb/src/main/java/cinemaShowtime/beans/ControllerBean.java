package cinemaShowtime.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerBean", eager = true)
@SessionScoped
public class ControllerBean {

	private CityBean cityBean;
	private MovieCatalogueBean movieCatalogueBean;
	private MovieRankingBean movieRankingBean;
	private CinemaPreviewBean cinemaPreviewBean;
	private CinemaPremiereBean cinemaPremiereBean;
	private HomePageBean homePageBean;

	public ControllerBean() {
		long startTime = System.currentTimeMillis();

		cityBean = new CityBean();
		movieCatalogueBean = new MovieCatalogueBean();
		movieRankingBean = new MovieRankingBean();
		cinemaPreviewBean = new CinemaPreviewBean();
		cinemaPremiereBean = new CinemaPremiereBean();
		homePageBean = new HomePageBean();

		long stopTime = System.currentTimeMillis();
		System.out.println("MainPage start in " + ((stopTime - startTime) / 1000) + "second");
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public MovieDetailBean getMovieDetailBean() {
		return MovieDetailBean.getInstance();
	}

	public MovieCatalogueBean getMovieCatalogueBean() {
		return movieCatalogueBean;
	}

	public MovieRankingBean getMovieRankingBean() {
		return movieRankingBean;
	}

	public CinemaPreviewBean getCinemaPreviewBean() {
		return cinemaPreviewBean;
	}

	public CinemaPremiereBean getCinemaPremiereBean() {
		return cinemaPremiereBean;
	}

	public void setCinemaPremiereBean(CinemaPremiereBean cinemaPremiereBean) {
		this.cinemaPremiereBean = cinemaPremiereBean;
	}

	public HomePageBean getHomePageBean() {
		return homePageBean;
	}

	public void setHomePageBean(HomePageBean homePageBean) {
		this.homePageBean = homePageBean;
	}

}
