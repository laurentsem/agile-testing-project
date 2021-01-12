package test.functional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class FunctionalTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Library/Java/JUNIT/chromedriver");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        driver = new ChromeDriver();
        // Seems no more working in last Chrome versions
        // driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    // Test de la Story #1-homepage (https://trello.com/c/WKTneu9o/1-homepage)
//    @Test
//    public void testHomepage() throws Exception {
//        driver.get("https://www.meetup.com/fr-FR/");
//        assertEquals(driver.getTitle(), "Partagez vos passions | Meetup");
//        String desc = driver.findElement(By.cssSelector("meta[name='description']"))
//                .getAttribute("content");
//        assertEquals(desc, "Partagez vos passions et faites bouger votre ville ! Meetup vous aide à rencontrer des personnes près de chez vous, autour de vos centres d'intérêt.");
//        String h1 = driver.findElement(By.cssSelector("h1")).getText();
//        assertEquals(h1, "Le monde vous tend les bras");
//        String punchline = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[2]/p/span")).getText();
//        assertEquals(punchline, "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions.");
//        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a"));
//        assertEquals(button.getText(), "Rejoindre Meetup");
//        assertEquals(button.getAttribute("href"), "https://www.meetup.com/fr-FR/register/");
//        assertEquals(button.getCssValue("background-color"), "rgba(241, 58, 89, 1)");
//    }

//    @Test
//    public void testRecherche() throws Exception {
//        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
//        String titleRecherche = driver.getTitle();
//        String h1Recherche = driver.findElement(By.cssSelector("h1")).getText();
//        assertThat(titleRecherche, h1Recherche, containsString("Nature et aventure"));
//        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
//        searchForm.findElement(By.id("mainKeywords")).isDisplayed();
//        searchForm.findElement(By.id("simple-radius")).isDisplayed();
//        searchForm.findElement(By.id("simple-location")).isDisplayed();
//        WebElement ulRecherche = driver.findElement(By.cssSelector("#simple-view-selector"));
//        List<WebElement> elements = ulRecherche.findElements(By.tagName("a"));
//        int i = 0;
//        for (WebElement e : elements) {
//            if (i == 0) {
//                assertEquals("Groupes", e.getText());
//            } else {
//                assertEquals("Calendrier", e.getText());
//            }
//            i++;
//        }
//        WebElement divTri = driver.findElement(By.id("simple-find-order"));
//        List<WebElement> elementA = divTri.findElements(By.tagName("li"));
//        for (WebElement e : elementA) {
//            WebElement a = e.findElement(By.tagName("a"));
//            if (a.getText() == "pertinence") {
//                assertEquals("selected", a.getAttribute("class"));
//                assertSame("pertinence", a.getText());
//            } else if (a.getText() == "plus récents") {
//                assertSame("plus récents", a.getText());
//            } else if (a.getText() == "nombre de membres") {
//                assertSame("nombre de membres", a.getText());
//            } else if (a.getText() == "proximité") {
//                assertSame("proximité", a.getText());
//            }
//
//        }
//        WebElement buttonCalendar = driver.findElement(By.id("simple-view-selector-event"));
//        buttonCalendar.click();
//        WebElement searchResult = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul"));
//        assertEquals("list", searchResult.getAttribute("data-view"));
//        WebElement datepicker = driver.findElement(By.id("simple-date-selector"));
//        datepicker.isDisplayed();
//        List<WebElement> days = datepicker.findElements(By.tagName("a"));
//        Actions actionprovider = new Actions(driver);
//
//        for (WebElement cell : days) {
//            if (cell.getText().equals("24")) {
//                try{
//                    actionprovider.click(cell);
//                    actionprovider.build().perform();
//                    break;
//                }catch (Exception e){
//                    buttonCalendar.click();
//                }
//                WebElement ulList = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul/li[1]"));
//                assertEquals("24",ulList.getAttribute("data-day"));
//
//            }
//        }
//
//
//    }

    @Test
    public void testFicheMeetup() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/?_locale=fr-FR/");
        WebElement infoMeetup = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[1]/div/section/div/div[2]"));
        WebElement titleInfo = infoMeetup.findElement(By.cssSelector("h1 a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/",titleInfo.getAttribute("href"));
        WebElement location = infoMeetup.findElement(By.className("organizer-city"));
        location.isDisplayed();
        WebElement members = infoMeetup.findElement(By.className("groupHomeHeaderInfo-memberLink"));
        members.isDisplayed();
        WebElement organisation = infoMeetup.findElement(By.className("orgInfo-name-superGroup"));
        organisation.isDisplayed();
//        WebElement button = infoMeetup.findElement(By.id("actionButtonLink"));
//        button.isDisplayed();
//        assertEquals(button.getAttribute("href"), "https://www.meetup.com/fr-FR/promenades-et-randonnees/?action=join");
        WebElement backgroundImg = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[1]/div/section/div/div[1]/div/div/div"));
        backgroundImg.isDisplayed();

        WebElement divLi = driver.findElement(By.className("hscrollContainer"));
        List<WebElement> elementA = divLi.findElements(By.tagName("li"));
        for (WebElement e : elementA) {
            WebElement a = e.findElement(By.cssSelector("span"));
            if (a.getText() == "À propos") {
                assertSame("À propos", a.getText());
            } else if (a.getText() == "Événements") {
                assertSame("Événements", a.getText());
            } else if (a.getText() == "Membres") {
                assertSame("Membres", a.getText());
            } else if (a.getText() == "Photos") {
                assertSame("Photos", a.getText());
            }else if (a.getText() == "Discussions") {
                assertSame("Discussions", a.getText());
            }else if (a.getText() == "Plus") {
                assertSame("Plus", a.getText());
            }
        }
        WebElement eventsSoon = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/events/",eventsSoon.getAttribute("href"));


        WebElement eventsPast = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div[3]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/events/past/",eventsPast.getAttribute("href"));
        WebElement eventsPhoto = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[3]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/photos/",eventsPhoto.getAttribute("href"));
        WebElement eventsMembers = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[2]/section[2]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/members/", eventsMembers.getAttribute("href"));




    }

    // Test de la Story n ...
    // TODO
    // To Be Completed By Coders From Coding Factory

    @After
    public void tearDown() throws Exception {
        try {
            Thread.sleep(4000);
            driver.quit();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
