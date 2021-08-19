package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.Map;

public class Argument {

	private String val;
	private Integer offset;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Argument() {
	}

	/**
	 * 
	 * @param val
	 * @param offset
	 */
	public Argument(String val, Integer offset) {
		super();
		this.val = val;
		this.offset = offset;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
