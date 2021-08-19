package com.accenture.api.test.reporting;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.gherkin.model.Background;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ExtentReportBuilder {
	private List<String> jsonPaths = null;
	private Path reportPath = null;
	private IgnoreList ignoreListObj = null;
	private List<String> ignoreList = null;

	public ExtentReportBuilder(List<String> jsonPaths, String reportDir) {
		this.jsonPaths = jsonPaths;
		buildReportPath(reportDir);
		setIgnoreList();
	}

	public void generateReport() {
		ExtentHtmlReporter htmlReporter = null;
		ExtentReports extent = null;
		JSONTestResult[] jsonTestResult = null;

		if (jsonPaths.size() > 0) {
			htmlReporter = new ExtentHtmlReporter(reportPath.toString());
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);

			for (String fileName : jsonPaths) {
				try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
					Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
							.create();
					jsonTestResult = gson.fromJson(br, JSONTestResult[].class);

					for (JSONTestResult jsTestResult : jsonTestResult) {
						ExtentTest feature = extent.createTest(Feature.class, jsTestResult.getDescription());
						processElements(jsTestResult.getElements(), feature, jsTestResult.getTags());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			extent.flush();

		}

	}

	private void buildReportPath(String outputFolder) {
		// String reportName = "run_" + getReportName() + ".html";
		// updated to static file name to integrate with openshift pipeline
		String reportName = "testauto-report" + ".html";
		reportPath = Paths.get(outputFolder);

		// Create directory if it does not exist
		try {
			if (!Files.exists(reportPath)) {
				Files.createDirectory(reportPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Build the report path
		reportPath = Paths.get(outputFolder + reportName);
	}

	private void processElements(List<Element> testResultElements, ExtentTest feature, List<Tag> featureTags) {
		int scenarioOutlineCtr = 0;

		for (Element el : testResultElements) {
			switch (el.getKeyword()) {
			case "Background":
				ExtentTest background = feature.createNode(Background.class, " ");
				processSteps(el.getSteps(), background);
				break;
			case "Scenario":
				ExtentTest scenario = feature.createNode(Scenario.class, el.getName());
				processSteps(el.getSteps(), scenario);
				processTags(el.getTags(), scenario);
				processTags(featureTags, scenario);
				break;
			case "Scenario Outline":
				scenarioOutlineCtr++;
				String testName = el.getName() + ".DataRow " + scenarioOutlineCtr;
				ExtentTest scenarioOutline = feature.createNode(Scenario.class, testName);
				processSteps(el.getSteps(), scenarioOutline);
				processTags(el.getTags(), scenarioOutline);
				processTags(featureTags, scenarioOutline);
				break;
			default:
				break;
			}
		}
	}

	private void processSteps(List<Step> steps, ExtentTest curNode) {
		ExtentTest newNode = null;

		for (Step curStep : steps) {
			switch (curStep.getKeyword().trim()) {
			case "*":
				addStepResult(curNode, curStep, "Given");
				break;
			case "Given":
			case "When":
			case "Then":
			case "And":
			case "But":
				addStepResult(curNode, curStep, curStep.getKeyword().trim());
				break;
			default:
				break;
			}
		}
	}

	private void addStepResult(ExtentTest currentExtStep, Step stepData, String keyword) {
		String status = stepData.getResult().getStatus();
		String docStringVal;
		DocString docString = stepData.getDocString();
		ExtentTest newExtentStep = null;
		Markup logText;

		// Process step if not in the ignore list
		if (!isStepInIgnoreList(stepData.getName().trim(), status)) {
			try {
				newExtentStep = currentExtStep.createNode(new GherkinKeyword(keyword), stepData.getName());

				if (docString != null) {
					docStringVal = docString.getValue();
					logText = MarkupHelper.createCodeBlock(docStringVal);
				} else {
					logText = MarkupHelper.createLabel(status, ExtentColor.BLACK);
				}
				docStringVal = (docString != null) ? docString.getValue() : status;
				Markup m = MarkupHelper.createCodeBlock(docStringVal);

				switch (status) {
				case "passed":
					newExtentStep.pass(logText);
					break;
				case "failed":
					newExtentStep.fail(logText);
					newExtentStep.fail(MarkupHelper.createCodeBlock(stepData.getResult().getErrorMsg()));
					break;
				case "skipped":
					newExtentStep.skip(logText);
					break;
				default:
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void processTags(List<Tag> tags, ExtentTest curNode) {
		for (Tag curTag : tags) {
			curNode.assignCategory(curTag.getName());
		}
	}

	private Boolean isStepInIgnoreList(String step, String status) {
		if (status.equals("passed")) {
			for (String ignoreVal : ignoreList) {
				ignoreVal = "^" + ignoreVal + ".*";
				if (step.matches(ignoreVal)) {
					return true;
				}
			}
		} else {
			return false;
		}

		return false;
	}

	private String getReportName() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.hhmmss");
		return sdf.format(date);
	}

	private void setIgnoreList() {
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
		ignoreList = new ArrayList<String>();

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