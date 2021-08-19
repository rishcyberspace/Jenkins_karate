package com.accenture.api.test.reporting;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONTestResult {

	private Integer line;
	private List<Element> elements = Collections.<Element>emptyList();
	private String name;
	private String description;
	private String id;
	private String keyword;
	private String uri;
	private List<Tag> tags = Collections.<Tag>emptyList();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private String fileName;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public JSONTestResult() {
	}

	/**
	 * 
	 * @param id
	 * @param description
	 * @param keyword
	 * @param name
	 * @param line
	 * @param uri
	 * @param elements
	 */
	public JSONTestResult(Integer line, List<Element> elements, String name, String description, String id,
			String keyword, String uri) {
		super();
		this.line = line;
		this.elements = elements;
		this.name = name;
		this.description = description;
		this.id = id;
		this.keyword = keyword;
		this.uri = uri;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getParentFolderName() {
		return Paths.get(uri).getParent().toString();
	}

}
