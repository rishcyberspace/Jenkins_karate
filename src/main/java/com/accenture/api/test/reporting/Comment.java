package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.Map;

public class Comment {

	private Integer line;
	private String value;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Comment() {
	}

	/**
	 * 
	 * @param value
	 * @param line
	 */
	public Comment(Integer line, String value) {
		super();
		this.line = line;
		this.value = value;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
