package com.zzl.common.pojo;

import java.io.Serializable;

public class PictureResult implements Serializable{
	
	private Integer error;
	
	private String url;
	
	private String message;

	
	public PictureResult(Integer error, String url) {
		this.error = error;
		this.url = url;
	}
	
	public PictureResult(Integer error, String url, String errorMessage) {
		this.error = error;
		this.url = url;
		this.message = errorMessage;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
