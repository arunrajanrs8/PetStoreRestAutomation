package api.reporting;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{

	private static ExtentReports extentReports;
	private String reportName;
	private String reportPath;
	
	@Override
    public void onStart(ITestContext context) {
		
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		reportName = "Test-Report_"+timestamp+".html";
		reportPath = System.getProperty("user.dir")+"//Reports//"+reportName;
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setDocumentTitle("RestAssured Automation");
		sparkReporter.config().setReportName("Pet Users API");
		
		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);
		extentReports.setSystemInfo("Application", "Pet Store Users API");
		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReports.setSystemInfo("Environment", "QA");

    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getName());
        for (String group : result.getMethod().getGroups()) {
            test.assignCategory(group);
        }
        ExtentManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
	    ExtentManager.getTest().skip(result.getThrowable());
	}

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

}
