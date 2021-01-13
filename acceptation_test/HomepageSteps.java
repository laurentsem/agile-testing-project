package acceptation_test;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HomepageSteps {

	public static WebDriver driver;

	@Before
	public void beforeScenario() {
		System.setProperty("webdriver.chrome.driver","/Library/Java/JUNIT/chromedriver");
		driver = new ChromeDriver();
		// Seems no more working in last Chrome versions
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Given("^je suis sur la homepage$")
	public void je_suis_sur_la_homepage() throws Throwable {
		driver.get("https://www.meetup.com/fr-FR/");
	}

	@Then("^le titre doit être \"([^\"]*)\"$")
	public void le_titre_doit_être(String arg1) throws Throwable {
	    assertEquals(driver.getTitle(), arg1);
	}

	@Then("^la description contient \"([^\"]*)\"$")
	public void la_description_doit_être(String arg1) throws Throwable {
		// By CSS Selector
		assertTrue(driver.findElement(By.cssSelector("meta[name='description']")).getAttribute("content").contains(arg1));
		// By XPATH, si vous préférez...
	    // assertEquals(driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content"), arg1);
	}

	@Then("^la punchline titre doit être \"([^\"]*)\"$")
	public void la_punchline_h_doit_être(String arg1) throws Throwable {
		String h1 = driver.findElement(By.cssSelector("h1")).getText();
		assertEquals(h1, arg1);
	}

	@Then("^la sous punchline doit être \"([^\"]*)\"$")
	public void la_sous_punchline_doit_être(String arg1) throws Throwable {
		String punchline = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[2]/p/span")).getText();
		assertEquals(punchline, arg1);

	}

	@Then("^le bouton doit être \"([^\"]*)\"$")
	public void le_bouton_doit_être(String arg1) throws Throwable {
		WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a"));
		assertEquals(button.getCssValue("background-color"), arg1);
	}

	@Then("^le bouton doit contenir \"([^\"]*)\"$")
	public void le_bouton_doit_contenir(String arg1) throws Throwable {
		WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a"));
		assertEquals(button.getText(), arg1);
	}

	@Then("^le bouton mène au lien \"([^\"]*)\"$")
	public void le_bouton_mène_au_lien(String arg1) throws Throwable {
		WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a"));
		assertEquals(button.getAttribute("href"), arg1);
	}



	@After
	public void afterScenario() {
		driver.quit();
	}

}
