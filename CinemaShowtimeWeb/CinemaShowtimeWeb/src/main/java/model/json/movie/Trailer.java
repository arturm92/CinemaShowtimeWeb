package model.json.movie;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trailer {

	private String language;
	@JsonProperty("is_official")
	private boolean official;
	@JsonProperty("trailer_files")
	private List<File> trailerFiles;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isOfficial() {
		return official;
	}
	public void setOfficial(boolean official) {
		this.official = official;
	}
	public List<File> getTrailerFiles() {
		return trailerFiles;
	}
	public void setTrailerFiles(List<File> trailerFiles) {
		this.trailerFiles = trailerFiles;
	}

}
