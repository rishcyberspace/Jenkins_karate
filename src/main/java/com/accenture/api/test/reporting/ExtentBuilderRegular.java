package com.accenture.api.test.reporting;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static com.accenture.api.test.reporting.ExtentReportBuilderUtils.*;

public class ExtentBuilderRegular {
	private List<JSONTestResult> fullTestList = null;
	private List<String> ignoreList = null;
	private String reportName;
	private Long scenarioDuration;
	private Long totalTestSetDuration;
	int stepCtr = 0;

	public ExtentBuilderRegular(List<JSONTestResult> fullTestList, List<String> ignoreList, Path reportPath) {
		this.fullTestList = fullTestList;
		this.ignoreList = ignoreList;
		setReportName(reportPath);
		scenarioDuration = 0L;
		totalTestSetDuration = 0L;
	}

	public void buildRegReport() {
		ExtentReports extent = null;
		ExtentHtmlReporter htmlReporter = null;

		htmlReporter = new ExtentHtmlReporter(reportName);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);

		for (JSONTestResult jsTestResult : fullTestList) {
			processElements(jsTestResult.getElements(), extent, jsTestResult.getTags());
		}

		extent.flush();
	}

	private void setReportName(Path reportPath) {
		reportName = "testauto-report.html";
		reportName = Paths.get(reportPath.toString(), reportName).toString();
	}

	private void processElements(List<Element> testResultElements, ExtentReports extent, List<Tag> featureTags) {
		int scenarioOutlineCtr = 0;
		List<Tag> addedTags = null;
		ExtentTest curTest = null;
		Element backgroundEl = null;
		Long testStart;

		for (Element el : testResultElements) {
			switch (el.getKeyword()) {
			case "Background":
				backgroundEl = el;
				break;
			case "Scenario":
				curTest = extent.createTest(el.getName());

				testStart = curTest.getModel().getStartTime().getTime(); // + totalTestSetDuration; commented because it
																			// adds time that is out of the execution
																			// time
				if (backgroundEl != null) {
					processSteps(curTest, backgroundEl.getSteps(), "Background", testStart);
				}
				processSteps(curTest, el.getSteps(), "Scenario", testStart);

				addedTags = new ArrayList<>();
				processTags(el.getTags(), curTest, addedTags);
				processTags(featureTags, curTest, addedTags);

				setTestDuration(curTest, testStart, scenarioDuration);

				backgroundEl = null;
				scenarioDuration = 0L;
				stepCtr = 0;
				break;
			case "Scenario Outline":
				scenarioOutlineCtr++;
				String testName = el.getName() + ".DataRow " + scenarioOutlineCtr;
				curTest = extent.createTest(testName);

				testStart = curTest.getModel().getStartTime().getTime(); // + totalTestSetDuration; commented because it
																			// adds time that is out of the execution
																			// time
				if (backgroundEl != null) {
					processSteps(curTest, backgroundEl.getSteps(), "Background", testStart);
				}
				processSteps(curTest, el.getSteps(), "Scenario", testStart);

				addedTags = new ArrayList<>();
				processTags(el.getTags(), curTest, addedTags);
				processTags(featureTags, curTest, addedTags);

				setTestDuration(curTest, testStart, scenarioDuration);

				backgroundEl = null;
				scenarioDuration = 0L;
				stepCtr = 0;
				break;
			default:
				break;
			}
		}
	}

	private void processSteps(ExtentTest curNode, List<Step> steps, String subStepDesc, Long testStartTime) {
		stepCtr = logExtentStep(curNode, Status.INFO, subStepDesc, testStartTime, null, stepCtr, totalTestSetDuration);

		for (Step curStep : steps) {
			addStepResult(curNode, curStep, curStep.getKeyword().trim(), testStartTime);
		}
	}

	private boolean addStepResult(ExtentTest currentExtStep, Step stepData, String keyword, Long testStartTime) {
		String status = stepData.getResult().getStatus();
		Long stepDuration = stepData.getResult().getDuration();
		DocString docString = stepData.getDocString();
		boolean stepAdded = false;
		String stepText = keyword + " " + stepData.getName();
		Status stepStatus = null;

		if (stepDuration != null && stepDuration > 0) {
			Long durationinMs = TimeUnit.NANOSECONDS.toMillis(stepDuration);
			scenarioDuration += durationinMs;
			totalTestSetDuration += durationinMs;
		}

		// Process step if not in the ignore list
		if (!isStepInIgnoreList(ignoreList, stepData.getName().trim(), status)) {
			stepStatus = convertToExtentStatus(status);

			stepCtr = logExtentStep(currentExtStep, stepStatus, stepText, testStartTime, docString, stepCtr,
					scenarioDuration);

			stepAdded = true;
		}
		return stepAdded;
	}

}