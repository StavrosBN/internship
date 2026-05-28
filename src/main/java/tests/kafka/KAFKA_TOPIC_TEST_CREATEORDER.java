package tests.kafka;

import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.kafka.endpoint.KafkaEndpoint;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.citrusframework.actions.ReceiveMessageAction.Builder.receive;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class KAFKA_TOPIC_TEST_CREATEORDER extends TestNGCitrusSpringSupport {

    @Autowired
    public KafkaEndpoint helloKafkaEndpoint;
    @CitrusResource
    public TestContext context;

    @Test
    @CitrusTest
    public void testIsAlive() {
        $(receive(helloKafkaEndpoint)
                .message()
                .type("json")
                .timeout(50000L)
                .validate(jsonPath()
                        .expression("templateName", "CreateOrder")));

    }
}
