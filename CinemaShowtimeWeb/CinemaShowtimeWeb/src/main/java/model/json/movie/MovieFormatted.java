package model.json.movie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import cinemaShowtime.utils.DateFormatter;
import model.json.ShowtimeDay;

public class MovieFormatted extends Movie {

	private List<ShowtimeDay> showtimeDayList;

	public String getTrailerURL() {
		if (getTrailers() != null) {
			return getTrailers().get(0).getTrailerFiles().get(0).getUrl().replace("watch?v=", "v/");
		}
		return null;
	}

	public String getRating() {
		if (getImdbRating() != null) {
			return getImdbRating();
		} else {
			return getTmdbRating();
		}
	}

	public String getImdbRating() {
		if (getRatings() != null) {
			Rating imdbRating = getRatings().getImdbRating();
			if (imdbRating != null && !imdbRating.getVoteCount().equals("0")) {
				return "(" + imdbRating.getValue() + " / " + imdbRating.getVoteCount() + ")";
			}
		}
		return null;
	}

	public String getTmdbRating() {
		if (getRatings() != null) {
			Rating tmdbRating = getRatings().getTmdbRating();
			if (tmdbRating != null && !tmdbRating.getVoteCount().equals("0")) {
				return "(" + tmdbRating.getValue() + " / " + tmdbRating.getVoteCount() + ")";
			}
		}
		return null;
	}

	public BigDecimal getRatingValue() {
		if (getRatings() != null) {
			Rating imdbRating = getRatings().getImdbRating();
			if (imdbRating != null) {
				return new BigDecimal(imdbRating.getValue());
			}
			Rating tmdbRating = getRatings().getTmdbRating();
			if (tmdbRating != null)
				return new BigDecimal(tmdbRating.getValue());
		}
		return BigDecimal.ZERO;
	}

	public Integer getRatingIntValue() {
		return getRatingValue().intValue();
	}

	public Integer getRatingShortIntValue() {
		BigDecimal divisor = new BigDecimal("2");
		BigDecimal shortValue = getRatingValue().divide(divisor, BigDecimal.ROUND_HALF_UP);
		return shortValue.intValue();
	}

	public String getGenreInfo() {
		String ret = "";
		if (getGenre() != null) {
			for (Genre elem : getGenre()) {
				ret += "[" + elem.getName() + "] ";
			}
		}
		return ret;
	}

	public String getCrewText() {
		String ret = "";
		if (getCrew() != null) {
			for (Person person : getCrew()) {
				ret += person.getJob() + " : " + person.getName() + "\n";
			}
		}
		return ret;
	}

	public String getCastText() {
		String ret = "";
		int i = 1;
		if (getCast() != null) {
			for (Person person : getCast()) {
				if (person.getCharacter() != null && person.getCharacter().length() > 0) {
					ret += person.getCharacter() + " : " + person.getName() + "\n";
					if (i == 9) {
						break;
					}
					i++;
				}
			}
		}
		return ret;
	}

	public String getFirstDirector() {
		String ret = "";
		if (getCrew() != null) {
			for (Person person : getCrew()) {
				if (person.getJob().equals("reżyser")) {
					ret += person.getJob() + " : " + person.getName() + "\n";
					return ret;
				}
			}
		}
		return ret;
	}

	public Object getSimpleAgeLimit() {
		if (getAgeLimit() != null) {
			Object val = getAgeLimit().get("PL");
			if (val == null) {
				val = getAgeLimit().get("DE");
				if (val == null) {
					val = getAgeLimit().get("GB");
				}
			}
			return val;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String getReleaseDateFormatted() {
		LinkedHashMap<String, Object> releaseDateMap;
		LinkedHashMap<String, String> map;
		releaseDateMap = getReleaseDate();
		if (releaseDateMap != null) {
			List<Object> date = (ArrayList<Object>) releaseDateMap.get("PL");
			if (date != null) {
				map = (LinkedHashMap<String, String>) date.get(0);
				return "premiera w Polsce: " + map.get("date");
			} else {
				date = (ArrayList<Object>) releaseDateMap.get("US");
				if (date != null) {
					map = (LinkedHashMap<String, String>) date.get(0);
					return "premiera na świecie: " + map.get("date");
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Date getReleaseDateInDateType() {
		LinkedHashMap<String, String> map;
		LinkedHashMap<String, Object> releaseDateMap;
		releaseDateMap = getReleaseDate();
		if (releaseDateMap != null) {
			List<Object> date = (ArrayList<Object>) releaseDateMap.get("PL");
			if (date != null) {
				map = (LinkedHashMap<String, String>) date.get(0);
			} else {
				date = (ArrayList<Object>) releaseDateMap.get("US");
				if (date != null) {
					map = (LinkedHashMap<String, String>) date.get(0);
				} else {
					return null;
				}
			}
			DateFormatter df = new DateFormatter();
			return df.parseString(map.get("date"));
		}
		return null;
	}

	public List<ShowtimeDay> getShowtimeDayList() {
		return showtimeDayList;
	}

	public void setShowtimeDayList(List<ShowtimeDay> showtimeDayList) {
		this.showtimeDayList = showtimeDayList;
	}

	public boolean isGalleryRendered() {
		if (getSceneImages() != null && getSceneImages().size() > 5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTrailerRendered() {
		if (getTrailers() != null) {
			return true;
		} else {
			return false;
		}
	}

}
