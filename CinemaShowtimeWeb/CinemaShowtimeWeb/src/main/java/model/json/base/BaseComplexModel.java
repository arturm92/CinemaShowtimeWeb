package model.json.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseComplexModel {

	@JsonProperty("meta_info")
	private Object metaInfo;

	public BaseComplexModel() {
	}

	public Object getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(Object metaInfo) {
		this.metaInfo = metaInfo;
	}
}
