package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.filters.Filter;
import cinemaShowtime.filters.FilterInterfaceImpl;
import cinemaShowtime.filters.ReloadInterface;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.Const;
import cinemaShowtime.utils.DateFormater;
import cinemaShowtime.utils.Logger;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "cinemaPremiereBean", eager = true)
@SessionScoped
public class CinemaPremiereBean extends FilterInterfaceImpl implements ReloadInterface {

	private Movies movies;
	private Movies moviePosters;

	private DateFormater df = new DateFormater();
	private Date dateFrom;

	public CinemaPremiereBean() {
		long startTime = System.currentTimeMillis();

		init();

		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	private void init() {
		setConfiguration(Filter.Configuration.PREMIERE);
		initFilter();
		dateFrom = df.getMonthFromToday(0);
		prepareCinemaPremiereMoviesList();
	}

	private void prepareCinemaPremiereMoviesList() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMovies(filter);
		MovieHelper.verifyList(movies, dateFrom, getMovieFilter().getSelectedGenreList());
		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);

		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

		// setCinemaPremiereMoviesList(movies.getMoviesWithPosterList());
		getMovieSorter().setMovies(movies);
		getMovieSorter().setSortType(false);
		getMovieSorter().dateSort();

	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, df.formatDateShort(dateFrom));
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_TO, df.formatDateShort(df.getMonthFromToday(1)));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Const.COUNTRIES);
		if (!getMovieFilter().getSelectedGenreList().isEmpty()) {
			String genre_ids = "";
			for (Genre genre : getMovieFilter().getSelectedGenreList()) {
				genre_ids += genre.getId() + ",";
			}
			filter.addFilterParam(ApiFilter.Parameter.GENRE_IDS, genre_ids);
		}
		return filter;
	}

	public void doFilter() {
		if (getMovieFilter().canFilter()) {
			prepareCinemaPremiereMoviesList();
		}
	}

	public void clickMovie() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			String movieId = ec.getRequestParameterMap().get("movieId");
			MovieDetailBean.getInstance().initMovieDetailBean(movieId);
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<MovieFormatted> getMovieList() {
		return movies.getList();
	}

	@Override
	public void reloadPage() {
		getMovieFilter().initGenreList();
		prepareCinemaPremiereMoviesList();
	}

}
