package com.accenture.api.test.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExtentReportBuilderUtils {

	private ExtentReportBuilderUtils() {
	}

	public static Boolean isStepInIgnoreList(List<String> ignoreList, String step, String status) {
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

	public static void processTags(List<Tag> tags, ExtentTest curNode, List<Tag> addedTags) {
		for (Tag curTag : tags) {
			if (!addedTags.contains(curTag)) {
				curNode.assignCategory(curTag.getName());
				addedTags.add(curTag);
			}
		}
	}

	public static Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static void setTestDuration(ExtentTest test, Long testStart, Long scenarioDuration) {
		test.getModel().setStartTime(getTime(testStart));
		test.getModel().setEndTime(getTime(testStart + scenarioDuration));
	}

	public static int logExtentStep(ExtentTest test, Status stepStatus, String stepText, Long testStartTime,
			DocString docString, int stepCtr, Long testDuration) {
		test.log(stepStatus, stepText);
		test.getModel().getLogContext().get(stepCtr++).setTimestamp(getTime(testStartTime + testDuration));

		if (docString != null) {
			test.log(stepStatus, MarkupHelper.createCodeBlock(docString.getValue()));
			test.getModel().getLogContext().get(stepCtr++).setTimestamp(getTime(testStartTime + testDuration));
		}

		return stepCtr;
	}

	public static Status convertToExtentStatus(String status) {
		Status stepStatus;

		switch (status) {
		case "passed":
			stepStatus = Status.PASS;
			break;
		case "failed":
			stepStatus = Status.FAIL;
			break;
		case "skipped":
			stepStatus = Status.SKIP;
			break;
		default:
			stepStatus = Status.INFO;
			break;
		}

		return stepStatus;
	}

}
