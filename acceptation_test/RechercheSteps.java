package acceptation_test;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class RechercheSteps {

    public static WebDriver driver;

    @Before
    public void beforeScenario() {
        System.setProperty("webdriver.chrome.driver", "/Library/Java/JUNIT/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        // Seems no more working in last Chrome versions
        // driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Given("^je suis sur recherche$")
    public void je_suis_sur_recherche() throws Throwable {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
    }

    @Then("^le titre doit contenir  \"([^\"]*)\"$")
    public void le_titre_doit_contenir(String arg1) throws Throwable {
        String titleRecherche = driver.getTitle();
        assertThat(titleRecherche, containsString(arg1));
    }

    @Then("^la balise titre dois contenir \"([^\"]*)\"$")
    public void la_balise_titre_dois_contenir(String arg1) throws Throwable {
        String h1Recherche = driver.findElement(By.cssSelector("h1")).getText();
        assertThat(h1Recherche, containsString(arg1));
    }

    @Then("^La page de recherche contient un bandeau de recherche$")
    public void la_page_de_recherche_contient_un_bandeau_de_recherche() throws Throwable {
        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
        searchForm.isDisplayed();
    }

    @Then("^contient le champ de recherche$")
    public void contient_le_champ_de_recherche() throws Throwable {
        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
        searchForm.findElement(By.id("mainKeywords")).isDisplayed();
    }

    @Then("^contient le rayon de recherche$")
    public void contient_le_rayon_de_recherche() throws Throwable {
        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
        searchForm.findElement(By.id("simple-radius")).isDisplayed();
    }

    @Then("^contient la ville de recherche$")
    public void contient_la_ville_de_recherche() throws Throwable {
        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
        searchForm.findElement(By.id("simple-location")).isDisplayed();
    }

    @Then("^contient un choix d'affichage de la liste entre Groupe et Calendrier$")
    public void contient_un_choix_d_affichage_de_la_liste_entre_Groupe_et_Calendrier() throws Throwable {
        WebElement ulRecherche = driver.findElement(By.cssSelector("#simple-view-selector"));
        List<WebElement> elements = ulRecherche.findElements(By.tagName("a"));
        int i = 0;
        for (WebElement e : elements) {
            if (i == 0) {
                assertEquals("Groupes", e.getText());
            } else {
                assertEquals("Calendrier", e.getText());
            }
            i++;
        }
    }


    @Then("^le tri par default est le tri par \"([^\"]*)\"$")
    public void le_tri_par_default_est_le_tri_par(String arg1) throws Throwable {
        WebElement divTri = driver.findElement(By.id("simple-find-order"));
        List<WebElement> elementA = divTri.findElements(By.tagName("li"));
        for (WebElement e : elementA) {
            WebElement a = e.findElement(By.tagName("a"));
            if (a.getText().equals(arg1)) {
                assertEquals("selected", a.getAttribute("class"));
            }
        }
    }

    @Then("^il y a quatre possibilités de \"([^\"]*)\"$")
    public void il_y_a_quatre_possibilités_de(String arg1) throws Throwable {
        WebElement divTri = driver.findElement(By.id("simple-find-order"));
        List<WebElement> elementA = divTri.findElements(By.tagName("li"));
        for (WebElement e : elementA) {
            WebElement a = e.findElement(By.tagName("a"));
            if (a.getText().equals(arg1)) {
                assertSame(arg1, a.getText());
                System.out.println("tri : "+ a.getText());
            }
        }
    }


    @After
    public void afterScenario() {
        driver.quit();
    }


}
