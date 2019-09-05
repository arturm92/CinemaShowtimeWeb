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
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.Consts;
import cinemaShowtime.utils.DateFormater;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "cinemaPreviewBean", eager = true)
@SessionScoped
public class CinemaPreviewBean extends FilterInterfaceImpl {

	private Movies movies;
	private Movies moviePosters;

	private DateFormater df = new DateFormater();
	private Date dateFrom;

	public CinemaPreviewBean() {
		long startTime = System.currentTimeMillis();

		setConfiguration(Filter.Configuration.PREVIEW);
		initFilter();
		dateFrom = df.getMonthFromToday(1);
		prepareCinemaPreviewMoviesList();

		long stopTime = System.currentTimeMillis();
		System.out.println("CinemaPreviewBean started in " + ((stopTime - startTime) / 1000) + " second");
	}

	private void prepareCinemaPreviewMoviesList() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);

		movies = ApiHelper.getMovies(filter);
		MovieHelper.verifyList(movies, dateFrom, getMovieFilter().getSelectedGenreList());
		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);

		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

		getMovieSorter().setMovies(movies);
		getMovieSorter().setSortType(true);
		getMovieSorter().dateSort();

	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.INCLUDE_UPCOMINGS, "true");
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, df.formatDateShort(dateFrom));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Consts.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Consts.COUNTRIES);
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
			prepareCinemaPreviewMoviesList();
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
}
