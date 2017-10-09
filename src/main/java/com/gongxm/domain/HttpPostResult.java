package com.gongxm.domain;

import java.io.InputStream;

/**
 * 
 * @author simple
 *
 */
public class HttpPostResult {
	private int statusCode;
	private InputStream stream;

	public HttpPostResult() {
	}

	public HttpPostResult(int statusCode, InputStream stream) {
		super();
		this.statusCode = statusCode;
		this.stream = stream;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public boolean isSuccess() {
		return statusCode == 200;
	}

}
