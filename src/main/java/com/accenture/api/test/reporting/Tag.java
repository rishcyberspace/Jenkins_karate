package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.Map;

public class Tag {

	private Integer line;
	private String name;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Tag() {
	}

	/**
	 * 
	 * @param name
	 * @param line
	 */
	public Tag(Integer line, String name) {
		super();
		this.line = line;
		this.name = name;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			Tag currentTag = (Tag) object;
			if (this.name.equals(currentTag.getName())) {
				result = true;
			}
		}
		return result;
	}
}
