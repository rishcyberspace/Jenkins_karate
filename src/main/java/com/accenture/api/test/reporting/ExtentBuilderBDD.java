package com.accenture.api.test.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Background;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.accenture.api.test.reporting.ExtentReportBuilderUtils.*;

public class ExtentBuilderBDD {
	private List<JSONTestResult> fullTestList = null;
	private List<String> ignoreList = null;
	private String reportName;
	private Long scenarioDuration;
	private Long totalTestSetDuration;

	public ExtentBuilderBDD(List<JSONTestResult> fullTestList, List<String> ignoreList, Path reportPath) {
		this.fullTestList = fullTestList;
		this.ignoreList = ignoreList;
		setReportName(reportPath);
		scenarioDuration = 0L;
		totalTestSetDuration = 0L;
	}

	public void buildBDDReport() {
		ExtentReports extent = null;
		ExtentHtmlReporter htmlReporter = null;

		htmlReporter = new ExtentHtmlReporter(reportName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);

		for (JSONTestResult jsTestResult : fullTestList) {
			ExtentTest feature = extent.createTest(Feature.class, jsTestResult.getDescription());
			processElements(jsTestResult.getElements(), feature, jsTestResult.getTags());

			Long featureStartTime = feature.getModel().getStartTime().getTime();
			feature.getModel().setEndTime(getTime(featureStartTime + totalTestSetDuration));
		}

		extent.flush();
	}

	private void setReportName(Path reportPath) {
		reportName = "testauto-report-bdd.html";
		reportName = Paths.get(reportPath.toString(), reportName).toString();
	}

	private void processElements(List<Element> testResultElements, ExtentTest feature, List<Tag> featureTags) {
		int scenarioOutlineCtr = 0;
		List<Tag> addedTags = null;
		Long subStepStart;

		for (Element el : testResultElements) {
			switch (el.getKeyword()) {
			case "Background":
				ExtentTest background = feature.createNode(Background.class, " ");

				subStepStart = background.getModel().getStartTime().getTime() + totalTestSetDuration;
				processSteps(el.getSteps(), background, subStepStart);

				setTestDuration(background, subStepStart, scenarioDuration);
				break;
			case "Scenario":
				addedTags = new ArrayList<>();
				ExtentTest scenario = feature.createNode(Scenario.class, el.getName());

				subStepStart = scenario.getModel().getStartTime().getTime() + totalTestSetDuration;
				processSteps(el.getSteps(), scenario, subStepStart);
				processTags(el.getTags(), scenario, addedTags);
				processTags(featureTags, scenario, addedTags);

				setTestDuration(scenario, subStepStart, scenarioDuration);

				scenarioDuration = 0L;
				break;
			case "Scenario Outline":
				addedTags = new ArrayList<>();
				scenarioOutlineCtr++;
				String testName = el.getName() + ".DataRow " + scenarioOutlineCtr;
				ExtentTest scenarioOutline = feature.createNode(Scenario.class, testName);

				subStepStart = scenarioOutline.getModel().getStartTime().getTime() + totalTestSetDuration;
				processSteps(el.getSteps(), scenarioOutline, subStepStart);
				processTags(el.getTags(), scenarioOutline, addedTags);
				processTags(featureTags, scenarioOutline, addedTags);

				setTestDuration(scenarioOutline, subStepStart, scenarioDuration);

				scenarioDuration = 0L;
				break;
			default:
				break;
			}
		}
	}

	private void processSteps(List<Step> steps, ExtentTest curNode, Long testStartTime) {
		Boolean usedGivenKeywordForStar = false;

		for (Step curStep : steps) {
			switch (curStep.getKeyword().trim()) {
			case "*":
				if (!usedGivenKeywordForStar) {
					if (addStepResult(curNode, curStep, "Given", testStartTime)) {
						usedGivenKeywordForStar = true;
					}
				} else {
					addStepResult(curNode, curStep, "And", testStartTime);
				}
				break;
			case "Given":
			case "When":
			case "Then":
			case "And":
			case "But":
				addStepResult(curNode, curStep, curStep.getKeyword().trim(), testStartTime);
				break;
			default:
				break;
			}
		}
	}

	private boolean addStepResult(ExtentTest currentExtStep, Step stepData, String keyword, Long testStartTime) {
		ExtentTest newExtentStep = null;
		String status = stepData.getResult().getStatus();
		Long stepDuration = stepData.getResult().getDuration();
		DocString docString = stepData.getDocString();
		boolean stepAdded = false;
		Status stepStatus = null;
		int stepCtr = 0;

		if (stepDuration != null && stepDuration > 0) {
			Long durationinMs = TimeUnit.NANOSECONDS.toMillis(stepDuration);
			scenarioDuration += durationinMs;
			totalTestSetDuration += durationinMs;
		}

		// Process step if not in the ignore list
		if (!isStepInIgnoreList(ignoreList, stepData.getName().trim(), status)) {
			try {
				newExtentStep = currentExtStep.createNode(new GherkinKeyword(keyword), stepData.getName());

				stepStatus = convertToExtentStatus(status);

				logExtentStep(newExtentStep, stepStatus, "", testStartTime, docString, stepCtr, scenarioDuration);

				stepAdded = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return stepAdded;
	}

}
