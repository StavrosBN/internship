package tests.mail;

import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.context.TestContext;
import org.citrusframework.mail.server.MailServer;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.citrusframework.actions.ReceiveMessageAction.Builder.receive;



public class MailTest extends TestNGCitrusSpringSupport {

    @CitrusResource
    public TestContext context;

    @Autowired
    public MailServer simpleMailServer;

    @Test
    @CitrusTest
    public void testReceiveMail() {
        $(receive()
                .endpoint(simpleMailServer)
                .timeout(30000L)
                .message()
                .header("citrus_mail_from", "sender@fakemail.com")
                .header("citrus_mail_to", "receiver@fakemail.com")
                .header("citrus_mail_subject", "Mail Test")
                .header("citrus_mail_date", "@ignore()@")
                .header("citrus_mail_bcc", "@ignore()@")

        );
    }
}
