package api.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{
	
	public Logger logger = LogManager.getLogger(this.getClass());
	private static ExtentReports extentReports;
	private static String reportPath;
	
	@Override
    public void onStart(ITestContext context) {
		getExtentReports(); // initialize once
    }

	private static synchronized ExtentReports getExtentReports() {
		
	    if (extentReports == null) {
	        reportPath = System.getProperty("user.dir")+"//Reports//index.html";
	        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
	        sparkReporter.config().setTheme(Theme.DARK);
	        sparkReporter.config().setDocumentTitle("RestAssured API Automation");
	        sparkReporter.config().setReportName("Pet-Users API");

	        extentReports = new ExtentReports();
	        extentReports.attachReporter(sparkReporter);
	        extentReports.setSystemInfo("Application", "Pet Store Users API");
	        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
	        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
	        extentReports.setSystemInfo("Environment", "QA");
	    }
	    return extentReports;
	}

    @Override
    public void onTestStart(ITestResult result) {
    	
    	String testName = result.getMethod().getMethodName() + " - " + result.getParameters().hashCode();
    	logger.info("Test ThreadId: "+testName);
    	ExtentTest test = getExtentReports().createTest(testName);
        for (String group : result.getMethod().getGroups()) {
            test.assignCategory(group);
        }
        ExtentManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test Passed");
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());
        ExtentManager.unload();
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
	    ExtentManager.getTest().skip(result.getThrowable());
	    ExtentManager.unload();
	}

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

}
