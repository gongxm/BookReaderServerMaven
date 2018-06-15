package com.gongxm.domain.response;

public class CategoryBookListResp extends ResponseResult {
	private int currentPage;

	public CategoryBookListResp() {
		super();
	}

	public CategoryBookListResp(int errcode, String errmsg) {
		super(errcode, errmsg);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
