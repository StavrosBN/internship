package tests.rest_api;

import lombok.extern.slf4j.Slf4j;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.message.MessageType;
import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.citrusframework.http.actions.HttpActionBuilder.http;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@Slf4j
public class SEND_SOAP_MESSAGE_WITH_NO_BODY extends TestNGCitrusSpringSupport {

    @Autowired
    public HttpClient kafkaUiAuthLocal;

    @CitrusResource
    public TestContext context;

    private String loadToken() {
        try {
            Path tokensPath = Path.of(
                    "C:\\Users\\sbantzis\\internship\\src\\main\\resources\\tokens.properties"
            );

            Properties props = new Properties();
            try (InputStream in = Files.newInputStream(tokensPath)) {
                props.load(in);
            }

            String token = props.getProperty("kafkaUiToken");
            if (token == null || token.isBlank()) {
                throw new IllegalStateException("kafkaUiToken not found/empty in tokens.properties");
            }
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load kafkaUiToken from tokens.properties", e);
        }
    }

    @Test
    @CitrusTest
    public void testKafkaUiMessagesHistory() {
        String token = loadToken();

        $(http().client(kafkaUiAuthLocal)
                .send()
                .post("/api/messages/send")
                .message()
                .type(MessageType.JSON)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                /*.body("{\n" +
                        " \"templateId\" :\"a0000000-0000-0000-0000-000000000010\", \n" +
                        " \"values\": \"{}\"\n" +
                        "}")
                 */
                .build());

        $(http().client(kafkaUiAuthLocal)
                .receive()
                .response(HttpStatus.BAD_REQUEST)
                .message()
                .validate(jsonPath()
                        .expression("$.error", "templateId and values are required")


                )
                .type(MessageType.JSON)
                .build());
    }
}
