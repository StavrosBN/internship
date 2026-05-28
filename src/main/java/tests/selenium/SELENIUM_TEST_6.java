package tests.selenium;

import lombok.extern.slf4j.Slf4j;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.selenium.endpoint.SeleniumBrowser;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.citrusframework.selenium.actions.SeleniumActionBuilder.selenium;

// Προσοχή στα σωστά static imports για τα actions

@Slf4j
public class SELENIUM_TEST_6 extends TestNGCitrusSpringSupport {

    @Autowired
    private SeleniumBrowser seleniumBrowser2;

    @Test
    @CitrusTest
    public void seleniumTest() throws InterruptedException {


        $(selenium().browser(seleniumBrowser2).start());

        Thread.sleep(2000);

        //Openning the browser
        $(selenium().browser(seleniumBrowser2)
                .click()
                .element(By.xpath("//a[@title='Topics']")));
        Thread.sleep(2000);

        $(selenium().browser(seleniumBrowser2)
                .click()
                .element(By.xpath("//a[@title='induction.soap.request.sent']")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser2)
                .click()
                .element(By.cssSelector("a[aria-disabled='false']")));
        Thread.sleep(1500);



        $(selenium().browser(seleniumBrowser2)
                .click()
                .element(By.cssSelector("tbody tr:nth-child(1) td:nth-child(2)")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser2)
                .click()
                .element(By.cssSelector("tbody tr:nth-child(1) td:nth-child(2)")));
        Thread.sleep(1500);




        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(selenium().stop(seleniumBrowser2));
    }
}