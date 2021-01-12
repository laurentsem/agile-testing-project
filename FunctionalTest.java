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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver();
        // Seems no more working in last Chrome versions
        // driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    // Test de la Story #1-homepage (https://trello.com/c/WKTneu9o/1-homepage)
    @Test
    public void testHomepage() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/");
        //CA n°1
        assertEquals(driver.getTitle(), "Partagez vos passions | Meetup");
        //CA n°2
        String desc = driver.findElement(By.cssSelector("meta[name='description']"))
                .getAttribute("content");
        assertEquals(desc, "Partagez vos passions et faites bouger votre ville ! Meetup vous aide à rencontrer des personnes près de chez vous, autour de vos centres d'intérêt.");
        //CA n°3
        String h1 = driver.findElement(By.cssSelector("h1")).getText();
        assertEquals(h1, "Le monde vous tend les bras");
        //CA n°4
        String punchline = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[2]/p/span")).getText();
        assertEquals(punchline, "Rejoignez un groupe local pour rencontrer du monde, tester une nouvelle activité ou partager vos passions.");
        //CA n°5
        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/div/section/div/div[3]/a"));
        assertEquals(button.getText(), "Rejoindre Meetup");
        assertEquals(button.getAttribute("href"), "https://www.meetup.com/fr-FR/register/");
        assertEquals(button.getCssValue("background-color"), "rgba(241, 58, 89, 1)");
    }

    @Test
    public void testRecherche() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/find/outdoors-adventure/");
        //CA n°1
        String titleRecherche = driver.getTitle();
        String h1Recherche = driver.findElement(By.cssSelector("h1")).getText();
        assertThat(titleRecherche, h1Recherche, containsString("Nature et aventure"));
        //CA n°2
        WebElement searchForm = driver.findElement(By.cssSelector("form[id='searchForm']"));
        searchForm.findElement(By.id("mainKeywords")).isDisplayed();
        searchForm.findElement(By.id("simple-radius")).isDisplayed();
        searchForm.findElement(By.id("simple-location")).isDisplayed();
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
        //CA n°3 et 4
        WebElement divTri = driver.findElement(By.id("simple-find-order"));
        List<WebElement> elementA = divTri.findElements(By.tagName("li"));
        for (WebElement e : elementA) {
            WebElement a = e.findElement(By.tagName("a"));
            if (a.getText() == "pertinence") {
                assertEquals("selected", a.getAttribute("class"));
                assertSame("pertinence", a.getText());
            } else if (a.getText() == "plus récents") {
                assertSame("plus récents", a.getText());
            } else if (a.getText() == "nombre de membres") {
                assertSame("nombre de membres", a.getText());
            } else if (a.getText() == "proximité") {
                assertSame("proximité", a.getText());
            }

        }
        //CA n°5
        WebElement buttonCalendar = driver.findElement(By.id("simple-view-selector-event"));
        buttonCalendar.click();
        WebElement searchResult = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul"));
        assertEquals("list", searchResult.getAttribute("data-view"));
        WebElement datepicker = driver.findElement(By.id("simple-date-selector"));
        datepicker.isDisplayed();
        //CA n°6
        List<WebElement> days = datepicker.findElements(By.tagName("a"));
        Actions actionprovider = new Actions(driver);

        for (WebElement cell : days) {
            if (cell.getText().equals("24")) {
                try{
                    actionprovider.click(cell);
                    actionprovider.build().perform();
                    break;
                }catch (Exception e){
                    buttonCalendar.click();
                }
                WebElement ulList = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/div[1]/ul/li[1]"));
                assertEquals("24",ulList.getAttribute("data-day"));

            }
        }
    }

    @Test
    public void testFicheMeetup() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/?_locale=fr-FR/");
        //CA n°1
        WebElement infoMeetup = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[1]/div/section/div/div[2]"));
        WebElement titleInfo = infoMeetup.findElement(By.cssSelector("h1 a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/", titleInfo.getAttribute("href"));
        WebElement location = infoMeetup.findElement(By.className("organizer-city"));
        location.isDisplayed();
        WebElement members = infoMeetup.findElement(By.className("groupHomeHeaderInfo-memberLink"));
        members.isDisplayed();
        WebElement organisation = infoMeetup.findElement(By.className("orgInfo-name-superGroup"));
        organisation.isDisplayed();
        WebElement button = driver.findElement(By.id("actionButtonLink"));
        button.isDisplayed();
        assertEquals(button.getAttribute("href"), "https://www.meetup.com/fr-FR/promenades-et-randonnees/?action=join");
        WebElement backgroundImg = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[1]/div/section/div/div[1]/div/div/div"));
        backgroundImg.isDisplayed();
        //CA n°2
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
            } else if (a.getText() == "Discussions") {
                assertSame("Discussions", a.getText());
            } else if (a.getText() == "Plus") {
                assertSame("Plus", a.getText());
            }
        }
        //CA n°3
        WebElement eventsSoon = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/events/", eventsSoon.getAttribute("href"));
        WebElement eventsPast = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[2]/div[3]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/events/past/", eventsPast.getAttribute("href"));
        WebElement eventsPhoto = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[1]/section[3]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/photos/", eventsPhoto.getAttribute("href"));
        WebElement eventsMembers = infoMeetup.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[3]/main/div[4]/div/div/div/div[2]/section[2]/div[1]/div[2]/a"));
        assertEquals("https://www.meetup.com/fr-FR/promenades-et-randonnees/members/", eventsMembers.getAttribute("href"));

        //CA n°5
        Actions actions = new Actions(driver);
        actions.click(button);
        actions.build().perform();
        try {
            Thread.sleep(2000);
            WebElement websiteAccountLink = driver.findElement(By.cssSelector("a[class='link display--inlineBlock']"));
            assertEquals("https://www.meetup.com/fr-FR/login/?urlname=promenades-et-randonnees&groupId=4629342&returnUri=https%3A%2F%2Fwww.meetup.com%2Ffr-FR%2Fpromenades-et-randonnees%2F%3Faction%3Djoin&c=4629342&chapterId=4629342&chapterContext=true", websiteAccountLink.getAttribute("href"));
            WebElement facebookLink = driver.findElement(By.cssSelector(".signUpModal-facebook"));
            assertEquals("https://www.facebook.com/v3.3/dialog/oauth?client_id=2403839689&redirect_uri=https%3A%2F%2Fsecure.meetup.com%2Fties%2Ffacebook%2F&scope=email%2Cuser_friends&state=returnUri%3Dhttps%253A%252F%252Fwww.meetup.com%252Ffr-FR%252Fpromenades-et-randonnees%252F%253Faction%253Djoin%26c%3D4629342%26chapterId%3D4629342%26chapterContext%3Dtrue", facebookLink.getAttribute("href"));
            WebElement googleLink = driver.findElement(By.cssSelector(".signUpModal-google"));
            assertEquals("https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=855636443875-pmqkjkrj33pvp0t1ghecgp4f3l746856.apps.googleusercontent.com&redirect_uri=https://secure.meetup.com/ties/google/&response_type=code&scope=profile%20email&state=returnUri%3Dhttps%253A%252F%252Fwww.meetup.com%252Ffr-FR%252Fpromenades-et-randonnees%252F%253Faction%253Djoin%26c%3D4629342%26chapterId%3D4629342%26chapterContext%3Dtrue", googleLink.getAttribute("href"));
            WebElement appleLink = driver.findElement(By.cssSelector(".signUpModal-apple"));
            assertEquals("https://appleid.apple.com/auth/authorize?client_id=com.meetup.web&redirect_uri=https://secure.meetup.com/ties/apple&response_type=code%20id_token&scope=name%20email&response_mode=form_post&state=returnUri%3Dhttps%253A%252F%252Fwww.meetup.com%252Ffr-FR%252Fpromenades-et-randonnees%252F%253Faction%253Djoin%26c%3D4629342%26chapterId%3D4629342%26chapterContext%3Dtrue", appleLink.getAttribute("href"));
            WebElement signUp = driver.findElement(By.cssSelector(".signUpModal-email"));
            assertEquals("https://www.meetup.com/register/?method=email&returnUri=https%3A%2F%2Fwww.meetup.com%2Ffr-FR%2Fpromenades-et-randonnees%2F%3Faction%3Djoin&c=4629342&chapterId=4629342&chapterContext=true", signUp.getAttribute("href"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //CA n°6
        driver.get("https://www.meetup.com/fr-FR/promenades-et-randonnees/?_locale=fr-FR/");
        WebElement contact = driver.findElement(By.cssSelector(".orgInfo-message"));
        Actions actions1 = new Actions(driver);
        actions1.click(contact);
        actions1.build().perform();
        try{
            Thread.sleep(2000);
            String url = driver.getCurrentUrl();
            assertEquals(url, "https://secure.meetup.com/fr-FR/login/?errors=member.not_logged_in&returnUri=https%3A%2F%2Fsecure.meetup.com%2Ffr-FR%2Fmessages");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testJobs() throws Exception {
        driver.get("https://www.meetup.com/fr-FR/careers/");
        //CA n°1
        WebElement desc = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/div[1]/p"));
        assertEquals(desc.getText(), "Join our team, find your people");

        //CA n°2
        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[1]/section/div/a"));
        assertEquals(button.getText(), "Explore Opportunities");
        assertEquals(button.getCssValue("background-color"), "rgba(241, 58, 89, 1)");


        //CA n°3
        Actions actions = new Actions(driver);
        actions.click(button);
        actions.build().perform();
        WebElement opportunitiesLink = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[4]/section[2]/div[1]/span/a"));
        assertEquals("https://www.meetup.com/careers/all-opportunities",opportunitiesLink.getAttribute("href"));

        //CA n°4
        driver.get("https://www.meetup.com/fr-FR/careers/");
        WebElement punchline = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/div/p/span"));
        assertEquals("Perks and benefits",punchline.getText());
        WebElement divLi = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[3]/main/div[6]/section/div/ul"));
        List<WebElement> elementLi = divLi.findElements(By.tagName("li"));
        for (WebElement img : elementLi) {
            WebElement imgDisplayed = img.findElement(By.cssSelector("img"));
            System.out.println(imgDisplayed.getAttribute("src"));
            imgDisplayed.isDisplayed();
        }
        for (WebElement textDiv : elementLi){
            WebElement textDisplayed = textDiv.findElement(By.cssSelector("p"));
            textDisplayed.isDisplayed();
        }


    }

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
