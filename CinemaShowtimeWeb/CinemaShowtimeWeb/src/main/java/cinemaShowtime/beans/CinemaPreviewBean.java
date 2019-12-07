package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import cinemaShowtime.filters.ApiFilter;
import cinemaShowtime.filters.Filter;
import cinemaShowtime.filters.MovieFilter;
import cinemaShowtime.filters.MovieSorter;
import cinemaShowtime.filters.PageFilter;
import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.helpers.MovieHelper;
import cinemaShowtime.utils.AppParameter;
import cinemaShowtime.utils.Logger;
import model.json.complex.Movies;
import model.json.movie.Genre;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "cinemaPreviewBean", eager = true)
@SessionScoped
public class CinemaPreviewBean extends BaseBean {

	private Movies movies;
	private Movies moviePosters;
	private Date dateFrom;
	private PageFilter pageFilter;

	public CinemaPreviewBean() {
		Logger.logCreateBeanInfo("CinemaPreviewBean");
	}

	@PostConstruct
	private void init() {
		long startTime = System.currentTimeMillis();

		initPageFilter();
		dateFrom = getDateFormatter().getMonthFromToday(1);
		prepareCinemaPreviewMoviesList();
		setPrepared(true);
		
		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	private void initPageFilter() {
		pageFilter = new PageFilter(getAccountBean());
		pageFilter.setConfiguration(Filter.Configuration.NOW_SHOWING);
		pageFilter.initFilter();
	}

	public MovieFilter getMovieFilter() {
		return pageFilter.getMovieFilter();
	}

	public MovieSorter getMovieSorter() {
		return pageFilter.getMovieSorter();
	}

	private void prepareCinemaPreviewMoviesList() {
		Logger.log("PREPARING DATA");

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
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, getDateFormatter().formatDateShort(dateFrom));
		filter.addFilterParam(ApiFilter.Parameter.LANG, AppParameter.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, AppParameter.COUNTRIES);
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
			getMovieDetailBean().initMovieDetailBean(movieId);
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
		if (!isPrepared()) {
			getMovieFilter().initGenreList();
			prepareCinemaPreviewMoviesList();
		}
		setPrepared(false);
	}
}
