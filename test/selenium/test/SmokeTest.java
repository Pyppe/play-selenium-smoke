package selenium.test;

import java.util.HashMap;

import org.junit.Test;

import play.mvc.Router;
import selenium.SeleniumBaseTest;

public class SmokeTest extends SeleniumBaseTest {
	
	@Test
	public void doTheSmokeTest() throws Exception {
		driver.get(smokeUrl());
		assertTrue(driver.getPageSource().contains("Alles gut"));
	}
	
	private static String smokeUrl() {
		return Router.getFullUrl("Application.smoke", new HashMap<String, Object>());
	}
	
}
