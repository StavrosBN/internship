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
public class SELENIUM_TEST_5 extends TestNGCitrusSpringSupport {

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


        //SubmitClaim
        $(selenium().browser(seleniumBrowser)
                .click()
                .element(By.xpath("//div[normalize-space()='SubmitClaim']")));
        Thread.sleep(1000);


        $(selenium().browser(seleniumBrowser)
                .setInput("TEST-POL")
                .element(By.id("field-policyNumber")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("health")
                .element(By.id("field-claimType")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("2026-05-25")
                .element(By.id("field-incidentDate")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("Damaged Due to Sun Exposure")
                .element(By.id("field-description")));
        Thread.sleep(1000);

        $(selenium().browser(seleniumBrowser)
                .setInput("1200.00")
                .element(By.id("field-amount")));
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