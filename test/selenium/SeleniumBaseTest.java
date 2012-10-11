package selenium;

import org.apache.commons.lang.BooleanUtils;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import play.Logger;
import play.Play;
import play.db.jpa.JPAPlugin;
import play.test.FunctionalTest;

public abstract class SeleniumBaseTest extends FunctionalTest {
	
	public static final int DEFAULT_WAIT_TIMEOUT = 0; // in seconds
	public static final String BASE_URL;
	protected static WebDriver driver;
	
	@Rule
	public static ScreenshotTestRule screenshotTestRule;
	
	static {
		BASE_URL = "http://localhost:" + Play.configuration.getProperty("http.port");
		Logger.info("Using Selenium BASE_URL: " + BASE_URL);
	}

	@BeforeClass
	public static void init() throws Exception {
		Assume.assumeTrue(skipSeleniumTests() == false);
		driver = new FirefoxDriver();
		screenshotTestRule = new ScreenshotTestRule(driver);
	}

	@AfterClass
	public static void destroy() {
		if (driver != null) {
			driver.quit();
		}
		JPAPlugin.closeTx(false);
	}
	
	protected static void closeAndStartTransaction() {
		JPAPlugin.closeTx(false);
		JPAPlugin.startTx(false);
	}
	
	private static boolean skipSeleniumTests() {
		boolean skipSeleniumTests = BooleanUtils.toBoolean(Play.configuration.getProperty("tests.skipSeleniumTests", "false"));
		if (skipSeleniumTests) {
			System.out.println("SKIPPING SELENIUM TESTS");
		}
		return skipSeleniumTests;
	}

}
