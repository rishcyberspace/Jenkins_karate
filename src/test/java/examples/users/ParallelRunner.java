package examples.users;

import com.accenture.api.test.runners.BaseRunner;
import com.intuit.karate.cucumber.CucumberRunner;
import com.intuit.karate.cucumber.KarateStats;

import cucumber.api.CucumberOptions;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

@CucumberOptions(
		features = {"classpath:myfeatures"}
		//Run all test
		//tags = "@End2End-user-v2"
		)
public class ParallelRunner extends BaseRunner {

	
    @BeforeClass
    public static void beforeClass() {
    	//Uncomment this line when running locally and provide env to use:
    	//threads: number of threads to be used
      	
    	//System.setProperty("karate.env", "dev");
  	
    	
    	//System.setProperty("threads", "3");

    }    
     	   
}