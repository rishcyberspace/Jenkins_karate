package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.Map;

public class Result {

	private Long duration;
	private String status;
	private String errorMessage;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Result() {
	}

	/**
	 * 
	 * @param duration
	 * @param status
	 */
	public Result(Long duration, String status, String errorMessage) {
		super();
		this.duration = duration;
		this.status = status;
		this.errorMessage = errorMessage;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMessage;
	}

	public String setErrorMsg(String errorMessage) {
		return this.errorMessage = errorMessage;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
