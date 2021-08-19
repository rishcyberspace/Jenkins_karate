package com.accenture.api.test.reporting;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {

	private Integer line;
	private String name;
	private String description;
	private String type;
	private String keyword;
	private List<Step> steps = Collections.<Step>emptyList();
	private String id;
	private List<Tag> tags = Collections.<Tag>emptyList();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Element() {
	}

	/**
	 * 
	 * @param tags
	 * @param id
	 * @param description
	 * @param keyword
	 * @param name
	 * @param line
	 * @param steps
	 * @param type
	 */
	public Element(Integer line, String name, String description, String type, String keyword, List<Step> steps,
			String id, List<Tag> tags) {
		super();
		this.line = line;
		this.name = name;
		this.description = description;
		this.type = type;
		this.keyword = keyword;
		this.steps = steps;
		this.id = id;
		this.tags = tags;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
