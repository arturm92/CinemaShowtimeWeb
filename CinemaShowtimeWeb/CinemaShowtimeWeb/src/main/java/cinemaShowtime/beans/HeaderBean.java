package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import model.json.movie.Movie;

@ManagedBean(name = "headerBean", eager = true)
@SessionScoped
public class HeaderBean {
	private List<Movie> headerMovies;

	public HeaderBean() {
		long startTime = System.currentTimeMillis();
		
		headerMovies = ApiHelper.getNewestMovies().getMoviesWithPosterList();
		
		long stopTime = System.currentTimeMillis();
		System.out.println("FirstPage start in " + ((stopTime - startTime) / 1000) + "second");
	}

	public void clickMovie() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			updateHeaderMovieList(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateHeaderMovieList(String movieId) {
		List<Movie> newHeaderMovies = new ArrayList<Movie>();
		List<Movie> tmp = new ArrayList<Movie>();
		boolean copy = false;
		for (Movie movie : headerMovies) {
			if (copy) {
				newHeaderMovies.add(movie);
			} else {
				if (movie.getId().compareTo(Long.valueOf(movieId)) == 0) {
					newHeaderMovies.add(movie);
					copy = true;
				} else {
					tmp.add(movie);
				}
			}
		}
		newHeaderMovies.addAll(tmp);
		headerMovies = newHeaderMovies;
	}

	public List<Movie> getHeaderMovies() {
		return headerMovies;
	}
}