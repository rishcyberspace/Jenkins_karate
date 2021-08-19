package com.accenture.api.test.runners;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.accenture.api.test.reporting.CustomReportBuilder;
import com.accenture.api.test.reporting.ExtentReportBuilder;
import com.intuit.karate.cucumber.CucumberRunner;
import com.intuit.karate.cucumber.KarateStats;

public class BaseRunner {
    
	
	 @Test
	    public void testParallel() {
		 	int threads = getNumOfThreads();
	        String karateOutputPath = "target/surefire-reports";
	        KarateStats stats = CucumberRunner.parallel(getClass(), threads, "target/surefire-reports");
	        generateExtentReport(karateOutputPath);
	        assertTrue("scenarios failed", stats.getFailCount() == 0);
	    }
	    
	    public static void generateExtentReport(String karateOutputPath) {
	        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
	        List<String> jsonPaths = new ArrayList(jsonFiles.size());
	        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath())); 
	        CustomReportBuilder rptBuilder = new CustomReportBuilder(jsonPaths, "target/extent-reports/", "ExtentRegular");
	        rptBuilder.generateReport();
	    }
	    
	    public static int getNumOfThreads() {
		 	String threads = System.getProperty("threads");
		 	System.out.println("Configured Threads: " + threads);
		 	
		 	if(threads == null) 
		 	{
		 		threads = "2";
		 	}
		 	
		 	int intThreads = Integer.parseInt(threads);
		 	return intThreads;
	    }
	
}
