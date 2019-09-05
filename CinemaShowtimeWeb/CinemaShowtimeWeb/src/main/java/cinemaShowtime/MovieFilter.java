package cinemaShowtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.json.movie.Genre;
import util.DateFormater;

public class MovieFilter {

	private boolean filterChanged = false;

	private List<Genre> genres;
	private List<String> selectedGenreIds;

	private List<String> filterYearList;
	private List<String> filteredYearList;

	public MovieFilter() {
		initGenreList();
		initFilterYearList();
	}

	private void initGenreList() {
		genres = ApiHelper.getGenres().removeNullGenres().getList();
		selectedGenreIds = new ArrayList<String>();

	}

	private void initFilterYearList() {
		filterYearList = new ArrayList<String>();
		int year = 2010;
		for (int i = 0; i < 12; i++) {
			filterYearList.add(String.valueOf(year));
			year++;
		}
		filteredYearList = new ArrayList<String>();
		DateFormater df = new DateFormater();
		filteredYearList.add(String.valueOf(df.getCurrentYear()));
	}

	public void setFilteredYearList(List<String> filteredYearList) {
		if (compareList(this.filteredYearList, filteredYearList)) {
			System.out.println("LATA ZOSTAŁY ZMIENIONE");
			updateFilterFlag(true);
		}
		this.filteredYearList = filteredYearList;
	}

	public boolean canFilter() {
		if (filterChanged && chcekFilterParameter()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean chcekFilterParameter() {
		if (!filteredYearList.isEmpty()) {
			int val = intVal(filteredYearList.get(0));
			if (val < 2016) {
				if (filteredYearList.size() > 2) {
					for (String year : filteredYearList) {
						int nextVal = intVal(year);
						if (nextVal - val > 1) {
							showMessage("Brakuje ciągłości w wybranych latach");
							return false;
						}
						val = nextVal;
					}
					showMessage("Spróbuj zmniejszyć zakres lat");
					return false;
				}
			}
		}
		return true;
	}

	private int intVal(String val) {
		return new BigDecimal(val).intValue();
	}

	private void showMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd parametrów filtra!", msg));
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public List<String> getSelectedGenreIds() {
		return selectedGenreIds;
	}

	public void setSelectedGenreIds(List<String> selectedGenreIds) {
		if (compareList(this.selectedGenreIds, selectedGenreIds)) {
			System.out.println("GATUNKI ZOSTAŁY ZMIENIONE");
			updateFilterFlag(true);
		}
		this.selectedGenreIds = selectedGenreIds;
	}

	public List<String> getFilterYearList() {
		return filterYearList;
	}

	public boolean compareList(List<?> list1, List<?> list2) {
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

	public List<String> getFilteredYearList() {
		return filteredYearList;
	}

}
