package cinemaShowtime.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.ApiHelper;
import cinemaShowtime.ApiFilter;
import cinemaShowtime.MovieHelper;
import model.json.complex.Movies;
import model.json.movie.MovieFormatted;
import model.json.movie.comparator.MovieRatingComparator;
import util.Consts;
import util.DateFormater;

@ManagedBean(name = "headerBean", eager = true)
@SessionScoped
public class HeaderBean {

	private Movies headerMovies;
	private Movies moviePosters;

	public HeaderBean() {
		long startTime = System.currentTimeMillis();

		ApiFilter filter = prepareFilter();
		headerMovies = ApiHelper.getNewestMovies(filter);
		Collections.sort(headerMovies.getList(), Collections.reverseOrder(new MovieRatingComparator()));
		headerMovies.setList(headerMovies.getList().subList(0, 20));
		
		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(headerMovies, moviePosters);

		long stopTime = System.currentTimeMillis();
		System.out.println("HeaderBean start in " + ((stopTime - startTime) / 1000) + "second");
	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		DateFormater df = new DateFormater();
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, df.formatDateShort(df.getMonthFromToday(-1)));
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_TO, df.formatDateShort(df.getMonthFromToday(1)));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Consts.COUNTRIES);
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
