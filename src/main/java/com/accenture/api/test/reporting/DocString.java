package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.Map;

public class DocString {

	private String contentType;
	private Integer line;
	private String value;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public DocString() {
	}

	/**
	 * 
	 * @param value
	 * @param line
	 * @param contentType
	 */
	public DocString(String contentType, Integer line, String value) {
		super();
		this.contentType = contentType;
		this.line = line;
		this.value = value;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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
