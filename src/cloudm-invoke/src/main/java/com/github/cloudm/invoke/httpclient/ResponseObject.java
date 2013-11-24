package com.github.cloudm.invoke.httpclient;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author andy.wan
 *
 */
public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3891416914747226374L;
	private long startTime;
	private long stopTime; 
	private long totalTime;
	private Object entity; 
	private Integer status;
	
	private String errorMessage;
	
	private String optionErrorMessage;
	
	private Map<String, String> headers;

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOptionErrorMessage() {
		return optionErrorMessage;
	}
	
	public void setOptionErrorMessage(String optionErrorMessage) {
		this.optionErrorMessage = optionErrorMessage;
	}
	
	@Override
	public String toString() {
		return "ResponseObject [status=" + status + ", entity=" + entity
				+ ", errorMessage=" + errorMessage + ", optionErrorMessage="
				+ optionErrorMessage + "]";
	}
}
