package com.accenture.api.test.reporting;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IgnoreList {

	private List<String> ignoreList = Collections.<String>emptyList();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public IgnoreList() {
	}

	/**
	 *
	 * @param ignoreList
	 */
	public IgnoreList(List<String> ignoreList) {
		super();
		this.ignoreList = ignoreList;
	}

	public List<String> getIgnoreList() {
		return ignoreList;
	}

	public void setIgnoreList(List<String> ignoreList) {
		this.ignoreList = ignoreList;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
