package com.gongxm.domain.request;

public class RecommendParam {

	private String category;
	private String type;

	public RecommendParam() {
		super();
	}

	public RecommendParam(String category, String type) {
		super();
		this.category = category;
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
