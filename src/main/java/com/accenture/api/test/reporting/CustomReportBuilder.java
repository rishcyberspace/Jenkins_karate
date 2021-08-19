package com.accenture.api.test.reporting;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CustomReportBuilder {
	private List<String> jsonPaths = null;
	private Path reportPath = null;
	private List<String> ignoreList = null;
	private String reportType;

	public CustomReportBuilder(List<String> jsonPaths, String reportDir, String reportType) {
		this.jsonPaths = jsonPaths;
		this.reportType = reportType;
		buildReportPath(reportDir);
		setIgnoreList();
	}

	public void generateReport() {
		List<JSONTestResult> fullTestList = null;

		if (!jsonPaths.isEmpty()) {
			fullTestList = getTestListAndSort(jsonPaths);

			switch (reportType) {
			case "ExtentBDD":
				ExtentBuilderBDD bddBuilder = new ExtentBuilderBDD(fullTestList, ignoreList, reportPath);
				bddBuilder.buildBDDReport();
				break;
			case "ExtentRegular":
			default:
				ExtentBuilderRegular regBuilder = new ExtentBuilderRegular(fullTestList, ignoreList, reportPath);
				regBuilder.buildRegReport();
				break;
			}
		}

	}

	private void buildReportPath(String outputFolder) {
		reportPath = Paths.get(outputFolder);

		// Create directory if it does not exist
		try {
			if (!reportPath.toFile().exists()) {
				Files.createDirectory(reportPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<JSONTestResult> getTestListAndSort(List<String> jsonPaths) {
		List<JSONTestResult> listOfTests = new ArrayList<>();
		JSONTestResult[] jsonTestResult = null;

		for (String fileName : jsonPaths) {
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
						.create();
				jsonTestResult = gson.fromJson(br, JSONTestResult[].class);

				if (jsonTestResult != null) {
					jsonTestResult[0].setFileName(fileName);
					listOfTests.add(jsonTestResult[0]);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		listOfTests.sort(Comparator.comparing(JSONTestResult::getParentFolderName)
				.thenComparing(JSONTestResult::getDescription));
		return listOfTests;
	}

	private void setIgnoreList() {
		IgnoreList ignoreListObj = null;
		String ignoreListString = System.getProperty("ignoreList");

		if (ignoreListString != null && !ignoreListString.isEmpty()) {
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
			ignoreListObj = gson.fromJson(ignoreListString, IgnoreList.class);
			ignoreList = ignoreListObj.getIgnoreList();
		} else {
			setDefaultIgnoreList();
		}
	}

	private void setDefaultIgnoreList() {
		ignoreList = new ArrayList<>();

		ignoreList.add("def");
		ignoreList.add("url");
		ignoreList.add("eval");
		ignoreList.add("string");
		ignoreList.add("xml");
		ignoreList.add("xmlstring");
		ignoreList.add("set");
		ignoreList.add("text");
		ignoreList.add("table");
		ignoreList.add("copy");
		ignoreList.add("configure");
		ignoreList.add("replace");
		ignoreList.add("remove");
		ignoreList.add("call \\[Set the ignore list as a system prop\\]");
	}

}
