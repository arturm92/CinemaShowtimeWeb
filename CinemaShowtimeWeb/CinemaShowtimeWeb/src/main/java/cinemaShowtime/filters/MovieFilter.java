package cinemaShowtime.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import cinemaShowtime.helpers.ApiHelper;
import cinemaShowtime.utils.Application;
import cinemaShowtime.utils.DateFormater;
import cinemaShowtime.utils.Logger;
import model.json.movie.Genre;

public class MovieFilter {

	private HashMap<String, Boolean> renderedMap;
	private boolean filterChanged = false;

	private List<Genre> genreList;
	private List<Genre> selectedGenreList;

	private List<String> yearList;
	private String selectedYear;

	private String filterMode;
	private boolean runtimeMovies = true;

	public MovieFilter() {
		initGenreList();
		initYearList();
	}

	private void initGenreList() {
		genreList = ApiHelper.getGenres().removeNullGenres().getList();
		Application.getInstance().setGenreList(genreList);
		selectedGenreList = new ArrayList<Genre>();
		Random random = new Random();
		int randomIndex = random.nextInt(genreList.size() - 1);
		selectedGenreList.add(genreList.get(randomIndex));
	}

	private void initYearList() {
		yearList = new ArrayList<String>();
		int year = 2010;
		for (int i = 0; i < 10; i++) {
			yearList.add(String.valueOf(year));
			year++;
		}
		DateFormater df = new DateFormater();
		setSelectedYear(String.valueOf(df.getCurrentYear()));
	}

	public boolean canFilter() {
		if (filterChanged) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getFilterModeList() {
		return Arrays.asList("TOP50", "TOP100", "TOP200");
	}

	public String getFilterMode() {
		if (filterMode == null) {
			return getFilterModeList().get(0);
		}
		return filterMode;
	}

	public void setFilterMode(String filterMode) {
		this.filterMode = filterMode;
	}

	public boolean isRuntimeMovies() {
		return runtimeMovies;
	}

	public void setRuntimeMovies(boolean runtimeMovies) {
		if (this.runtimeMovies != runtimeMovies) {
			updateFilterFlag(true);
		}
		this.runtimeMovies = runtimeMovies;
	}

	private void showMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd parametrów filtra!", msg));
	}

	public List<Genre> getGenreList() {
		return genreList;
	}

	public List<Genre> getSelectedGenreList() {
		return selectedGenreList;
	}

	public void setSelectedGenreList(List<Genre> selectedGenreList) {
		if (isDifferentList(this.selectedGenreList, selectedGenreList)) {
			Logger.log("ZMIENIONO GATUNEK");
			updateFilterFlag(true);
		}
		this.selectedGenreList = selectedGenreList;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		if (!selectedYear.equals(this.selectedYear)) {
		    Logger.log("ZMIENIONO ROK");
			updateFilterFlag(true);
		}
		this.selectedYear = selectedYear;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public boolean isDifferentList(List<?> list1, List<?> list2) {
		boolean diffrent = false;
		if (list1.size() == list2.size()) {
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i) == list2.get(i)) {
					continue;
				} else {
					diffrent = true;
				}
			}
		} else {
			diffrent = true;
		}
		return diffrent;
	}

	public void updateFilterFlag(boolean flag) {
		this.filterChanged = flag;
	}

	public HashMap<String, Boolean> getRenderedMap() {
		return renderedMap;
	}

	public void setRenderedMap(HashMap<String, Boolean> renderedMap) {
		this.renderedMap = renderedMap;
	}

	public boolean isRenderedRuntime() {
		return renderedMap.get(Filter.Field.RUNTIME);
	}

	public boolean isRenderedRankingType() {
		return renderedMap.get(Filter.Field.RANKING_TYPE);
	}
	
	public boolean isRenderedReleaseDate() {
		return renderedMap.get(Filter.Field.RELEASE_DATE);
	}
}