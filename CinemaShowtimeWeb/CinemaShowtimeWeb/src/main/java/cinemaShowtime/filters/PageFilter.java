package cinemaShowtime.filters;

import java.util.HashMap;

import cinemaShowtime.beans.AccountBean;

public class PageFilter implements FilterInterface {

	private MovieFilter movieFilter;
	private MovieSorter movieSorter;
	private String configuration;

	public PageFilter(AccountBean accountBean) {
		movieFilter = new MovieFilter(accountBean);
		movieSorter = new MovieSorter();
	}
	
	@Override
	public void initFilter() {
		initRenderedMap();
	}

	@Override
	public MovieFilter getMovieFilter() {
		return movieFilter;
	}

	@Override
	public MovieSorter getMovieSorter() {
		return movieSorter;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	@Override
	public void initRenderedMap() {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		if (configuration.equals(Filter.Configuration.CATALOGUE)) {
			map.put(Filter.Field.RUNTIME, true);
			map.put(Filter.Field.RANKING_TYPE, false);
			map.put(Filter.Field.RELEASE_DATE, true);
		} else if (configuration.equals(Filter.Configuration.RANKING)) {
			map.put(Filter.Field.RUNTIME, true);
			map.put(Filter.Field.RANKING_TYPE, true);
			map.put(Filter.Field.RELEASE_DATE, true);
		} else if (configuration.equals(Filter.Configuration.PREMIERE)
				|| configuration.equals(Filter.Configuration.PREVIEW)) {
			map.put(Filter.Field.RUNTIME, false);
			map.put(Filter.Field.RANKING_TYPE, false);
			map.put(Filter.Field.RELEASE_DATE, false);
		} else if (configuration.equals(Filter.Configuration.NOW_SHOWING)) {
			map.put(Filter.Field.RUNTIME, false);
			map.put(Filter.Field.RANKING_TYPE, false);
			map.put(Filter.Field.RELEASE_DATE, false);
		}
		movieFilter.setRenderedMap(map);
	}

}