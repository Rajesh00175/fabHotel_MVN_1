package sprint_1;
/**
 * 
 */


import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

/**
 * @author Rajesh.Kumar4
 *
 */
public class FailedTestRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestNG failedTest = new TestNG();
		List<String> list = new ArrayList<String>();
		list.add("C:\\Users\\rajesh.kumar41\\eclipse-workspace\\IGT_Training\\test-output\\testng-failed.xml");
		failedTest.setTestSuites(list);
		failedTest.run();

	}

}
