package cinemaShowtime.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

@ManagedBean(name = "movieCatalogueBean", eager = true)
@ViewScoped
public class MovieCatalogueBean extends FilterInterfaceImpl implements ReloadInterface {

	private Movies movies;
	private Movies moviePosters;

	public MovieCatalogueBean() {
		long startTime = System.currentTimeMillis();

		setConfiguration(Filter.Configuration.CATALOGUE);
		initFilter();
		prepareMovieCatalogueList();

		long stopTime = System.currentTimeMillis();
		Logger.logBeanStartTime(getClass().getName(), stopTime - startTime);
	}

	private void prepareMovieCatalogueList() {
		ApiFilter filter = prepareFilter();
		filter.setFields(ApiFilter.Field.MOVIE_STANDARD_FIELDS);
		movies = ApiHelper.getMoviesCatalogue(filter);
		MovieHelper.verifyList(movies, getDateFrom(), getMovieFilter().getSelectedGenreList());

		filter.deleteFilterParam(ApiFilter.Parameter.LANG);
		filter.setFields(ApiFilter.Field.MOVIE_POSTER_FIELDS);
		moviePosters = ApiHelper.getMoviesPosterEngishVersion(filter);
		moviePosters.fillMovieMap();
		MovieHelper.addPosterToMovie(movies, moviePosters);

		getMovieFilter().updateFilterFlag(false);
		getMovieSorter().setMovies(movies);
		getMovieSorter().setSortType(true);
		getMovieSorter().titleSort();
	}

	private ApiFilter prepareFilter() {
		ApiFilter filter = new ApiFilter();
		filter.addFilterParam(ApiFilter.Parameter.INCLUDE_OUTDATED,
				String.valueOf(!getMovieFilter().isRuntimeMovies()));
		filter.addFilterParam(ApiFilter.Parameter.LANG, Const.LANGUAGE);
		filter.addFilterParam(ApiFilter.Parameter.COUNTRIES, Const.COUNTRIES);
		if (!getMovieFilter().getSelectedYear().isEmpty()) {
			String dateFrom = getMovieFilter().getSelectedYear() + "-01-01";
			String dateTo = getMovieFilter().getSelectedYear() + "-12-31";
			filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_FROM, dateFrom);
			filter.addFilterParam(ApiFilter.Parameter.RELEASE_DATE_TO, dateTo);
		}
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
			prepareMovieCatalogueList();
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

	private Date getDateFrom() {
		DateFormater df = new DateFormater();
		if (!getMovieFilter().getSelectedYear().isEmpty()) {
			return df.parseString(getMovieFilter().getSelectedYear() + "-01-01");
		} else {
			return null;
		}
	}

	@Override
	public void reloadPage() {
		// TODO Auto-generated method stub
		
	}

}
