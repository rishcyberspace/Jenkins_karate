package com.accenture.api.test.reporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step {

	private Result result;
	private Integer line;
	private String name;
	private Match match;
	private String keyword;
	private DocString docString;
	private List<Comment> comments = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Step() {
	}

	/**
	 * 
	 * @param result
	 * @param keyword
	 * @param name
	 * @param line
	 * @param match
	 * @param comments
	 * @param docString
	 */
	public Step(Result result, Integer line, String name, Match match, String keyword, DocString docString,
			List<Comment> comments) {
		super();
		this.result = result;
		this.line = line;
		this.name = name;
		this.match = match;
		this.keyword = keyword;
		this.docString = docString;
		this.comments = comments;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
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

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public DocString getDocString() {
		return docString;
	}

	public void setDocString(DocString docString) {
		this.docString = docString;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
