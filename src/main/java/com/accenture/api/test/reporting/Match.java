package com.accenture.api.test.reporting;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match {

	private List<Argument> arguments = Collections.<Argument>emptyList();
	private String location;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Match() {
	}

	/**
	 * 
	 * @param arguments
	 * @param location
	 */
	public Match(List<Argument> arguments, String location) {
		super();
		this.arguments = arguments;
		this.location = location;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
