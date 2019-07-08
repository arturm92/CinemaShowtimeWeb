package model.json.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

	private String value;
	@JsonProperty("vote_count")
	private String voteCount;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}
}
