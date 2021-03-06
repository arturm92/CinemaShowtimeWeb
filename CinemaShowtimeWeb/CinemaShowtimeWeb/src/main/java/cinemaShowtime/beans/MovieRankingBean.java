package cinemaShowtime.beans;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

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
import model.json.movie.Movie;
import model.json.movie.MovieFormatted;

@ManagedBean(name = "movieRankingBean", eager = true)
@SessionScoped
public class MovieRankingBean extends BaseBean {

	private Movie selectedMovie;
	private Movies rankingMovies;
	private Movies moviePosters;
	private List<MovieFormatted> displayRankingList;

	private PageFilter pageFilter;

	public MovieRankingBean() {
		Logger.logCreateBeanInfo("MovieRankingBean");
	}

	@PostConstruct
	private void init() {
		long startTime = System.currentTimeMillis();

		initPageFilter();
		prepareDisplayRankingList();
		setPrepared(true);
		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);

	}

	private void initPageFilter() {
		pageFilter = new PageFilter(getAccountBean());
		pageFilter.setConfiguration(Filter.Configuration.NOW_SHOWING);
		pageFilter.initFilter();
	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();

		filter.addFilterParam(ApiFilter.Parameter.INCLUDE_OUTDATED,
				String.valueOf(!getMovieFilter().isRuntimeMovies()));

		String dateFrom = getMovieFilter().getSelectedYear() + "-01-01";
		String dateTo = getMovieFilter().getSelectedYear() + "-12-31";

		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, dateFrom);
		filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_TO, dateTo);

		filter.addFilterParam(ApiFilter.Parameter.LANG, AppParameter.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, AppParameter.COUNTRIES);
		return filter;
	}

	private void prepareDisplayRankingList() {
		Logger.log("PREPARING DATA");

		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		rankingMovies = ApiHelper.getMovies(filter);

		MovieHelper.verifyList(rankingMovies, null, getMovieFilter().getSelectedGenreList());

		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(rankingMovies, moviePosters);

		rankingMovies.setList(rankingMovies.getMoviesWithPosterList());
		getMovieSorter().setMovies(rankingMovies);
		getMovieSorter().ratingSort();
		addNumberToEachMovie();

		displayRankingList = rankingMovies.getList().subList(0, getMax(50));

		getMovieFilter().updateFilterFlag(false);
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

	private void addNumberToEachMovie() {
		int number = 1;
		for (Movie movie : rankingMovies.getList()) {
			movie.setNumberInList(number);
			number++;
		}
	}

	public void doFilter() {
		if (getMovieFilter().canFilter()) {
			prepareDisplayRankingList();
		}
		if (getMovieFilter().getFilterMode() != null || !getMovieFilter().getFilterMode().isEmpty()) {
			if (getMovieFilter().getFilterMode().equals("TOP50")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(50));
			} else if (getMovieFilter().getFilterMode().equals("TOP100")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(100));
			} else if (getMovieFilter().getFilterMode().equals("TOP200")) {
				displayRankingList = rankingMovies.getList().subList(0, getMax(200));
			}
		} else {
			displayRankingList = rankingMovies.getList().subList(0, getMax(50));
		}
	}

	private int getMax(int mode) {
		int size = rankingMovies.getList().size();
		if (size < mode) {
			return size;
		} else {
			return mode;
		}
	}

	public void select(SelectEvent selectEvent) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			getMovieDetailBean().initMovieDetailBean(selectedMovie.getId().toString());
			ec.redirect("/CinemaShowtimeWeb/movieDetail/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<MovieFormatted> getDisplayRankingList() {
		return displayRankingList;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	@Override
	public void reloadPage() {
		if (!isPrepared()) {
			getMovieFilter().initGenreList();
			prepareDisplayRankingList();
		}
		setPrepared(false);
	}

	public MovieFilter getMovieFilter() {
		return pageFilter.getMovieFilter();
	}

	public MovieSorter getMovieSorter() {
		return pageFilter.getMovieSorter();
	}

}