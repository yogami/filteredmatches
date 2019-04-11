package com.filteredmatches.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.filteredmatches.MainApp;
import com.filteredmatches.config.AppConfig;
import com.filteredmatches.service.IInitialDataSetupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class FilteredMatchesWebTest {

	private static final String API_MATCHES_URL = "/api/matches/";
	private static final String MATCHES_URL = "/matches/";
	private static final Integer USER_ID = 1;

	@Autowired
	@Qualifier("initialSetup")
	private IInitialDataSetupService initialDataSetupService;// = new
																// InitialDataSetupService();
	@Before
	public void setUp() throws Exception {

		// try {
		// initialDataSetupService.deleteDataFromDatabase();
		// } catch (Exception ex) {
		//
		// }
		MainApp.startOrStopApp("start");

	}

	@Test
	public void shouldRenderTheFilteredResultsDiv() throws Exception {
		WebDriver client = new HtmlUnitDriver();
		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		String pageSource = client.getPageSource();
		assertTrue(pageSource.contains("<div id=\"matchResults\">"));
	}



	@Test
	@Ignore
	public void shouldRenderAllMatchsOnInitialPageLoad() throws Exception {
		WebDriver client = new HtmlUnitDriver();
		

		client.get(MainApp.getServerURI() + MATCHES_URL + USER_ID);
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState")
						.equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(client, 5);
		wait.until(pageLoadCondition);
		List<WebElement> elements = client
				.findElements(By.cssSelector(".card"));
		assertEquals(24, elements.size());

	}

	@After
	public void tearDown() throws Exception {
		MainApp.startOrStopApp("stop");
		// initialDataSetupService.deleteDataFromDatabase();
	}

}
