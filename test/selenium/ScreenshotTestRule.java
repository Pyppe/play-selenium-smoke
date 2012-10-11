package selenium;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * This code was found at
 * http://blogs.steeplesoft.com/2012/01/grabbing-screenshots-of-failed-selenium-tests/
 */
public class ScreenshotTestRule implements TestRule {
    protected static final String SCREENSHOT_DIR = "test-output/selenium-screenshots/";
	private WebDriver driver;

	public ScreenshotTestRule(WebDriver driver) {
    	this.driver = driver;
	}

	public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenshot(description.getMethodName());
                    throw t; // rethrow to allow the failure to be reported to JUnit
                }
            }
         };
    }
	
    public void captureScreenshot(String fileName) {
        try {
            new File(SCREENSHOT_DIR).mkdirs(); // Ensure directory is there
            FileOutputStream out = new FileOutputStream(SCREENSHOT_DIR + "screenshot-" + fileName + ".png");
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close();
        } catch (Exception e) {
            // No need to crash the tests if the screenshot fails
        }
    }
}