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
public class SELENIUM_TEST_4 extends TestNGCitrusSpringSupport {

    @Autowired
    private SeleniumBrowser seleniumBrowser;

    @Test
    @CitrusTest
    public void seleniumTest() throws InterruptedException {


        $(selenium().browser(seleniumBrowser).start());


        // LOGIN
        $(selenium().browser(seleniumBrowser)
                .setInput("admin")
                .element(By.id("username")));
        $(selenium().browser(seleniumBrowser)
                .setInput("admin")
                .element(By.id("password")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//button")));
        Thread.sleep(1000);


        //GetPriceQuote
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[normalize-space()='GetPriceQuote']")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("automotive")
                .element(By.xpath("//select[@id='field-productCategory']")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("100")
                .element(By.id("field-quantity")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("26444")
                .element(By.id("field-deliveryPostcode")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("true")
                .element(By.id("field-urgent")));
        Thread.sleep(1000);


        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.className("send-btn")));
        Thread.sleep(1000);




        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(selenium().stop(seleniumBrowser));
    }
}