package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.Filter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "headerBean", eager = true)
@SessionScoped
public class HeaderBean {

	private Movies headerMovies;
	private Movies moviePosters;

	public HeaderBean() {
		long startTime = System.currentTimeMillis();

		Filter filter = prepareFilter();
		headerMovies = ApiHelper.getNewestMovies(filter);
		filter.deleteFilterParam(Filter.Parameter.LANG);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(headerMovies, moviePosters);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("HeaderBean start in " + ((stopTime - startTime) / 1000) + "second");
	}

	private Filter prepareFilter() {
		Filter filter = new Filter();
		DateFormater df = new DateFormater();
		filter.setFields(Filter.Field.MOVIE_POSTER_FIELDS);
		filter.addFilterParam(Filter.Parameter.RELEASE_DATE_FROM, df.recalculateDateByMonth(-4));
		filter.addFilterParam(Filter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(Filter.Parameter.COUNTRIES, Consts.COUNTRIES);
		return filter;
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
		List<MovieFormatted> newHeaderMovies = new ArrayList<MovieFormatted>();
		List<MovieFormatted> tmp = new ArrayList<MovieFormatted>();
		boolean copy = false;
		for (MovieFormatted movie : headerMovies.getList()) {
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
		headerMovies.setList(newHeaderMovies);
	}

	public List<MovieFormatted> getHeaderMoviesList() {
		return headerMovies.getMoviesWithPosterList();
	}
}
