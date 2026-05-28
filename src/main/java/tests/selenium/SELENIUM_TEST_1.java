package tests.selenium;

import lombok.extern.slf4j.Slf4j;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.citrusframework.selenium.endpoint.SeleniumBrowser;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

// Προσοχή στα σωστά static imports για τα actions
import static org.citrusframework.selenium.actions.SeleniumActionBuilder.selenium;

@Slf4j
public class SELENIUM_TEST_1 extends TestNGCitrusSpringSupport {

    @Autowired
    private SeleniumBrowser seleniumBrowser;

    @Test
    @CitrusTest
    public void seleniumTest() throws InterruptedException {

        // 1. Ξεκινάει τον Chrome browser
        $(selenium().browser(seleniumBrowser).start());


        // 4. Τώρα το πεδίο είναι ελεύθερο! Γράφουμε στο πεδίο αναζήτησης
        $(selenium().browser(seleniumBrowser)
                .setInput("admin")
                .element(By.id("username")));
        $(selenium().browser(seleniumBrowser)
                .setInput("admin")
                .element(By.id("password")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//button")));
        Thread.sleep(1500);


        //CreateOrder
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[normalize-space()='CreateOrder']")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.className("send-btn")));
        Thread.sleep(1500);

        //GetCustomerDetails
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[normalize-space()='GetCustomerDetails']")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.className("send-btn")));
        Thread.sleep(1500);

        //GetPriceQuote
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[@class='template-item-desc'][normalize-space()='Request a pricing quote for products']")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.className("send-btn")));
        Thread.sleep(1500);

        //SubmitClaim
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[@class='template-item-desc'][normalize-space()='Submit an insurance claim']")));
        Thread.sleep(1500);

        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.className("send-btn")));
        Thread.sleep(1500);



        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(selenium().stop(seleniumBrowser));
    }
}