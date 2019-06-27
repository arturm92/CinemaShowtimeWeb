package model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonModel {

	@JsonProperty("meta_info")
	private Object metaInfo;

	public JsonModel() {
	}

	public Object getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(Object metaInfo) {
		this.metaInfo = metaInfo;
	}
}
