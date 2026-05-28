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

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.citrusframework.http.actions.HttpActionBuilder.http;
import static org.citrusframework.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@Slf4j
public class REQUEST_TOKEN_WITH_NO_PASSWORD extends TestNGCitrusSpringSupport {

    @Autowired
    public HttpClient kafkaUiAuthLocal;

    @CitrusResource
    public TestContext context;

    @Test
    @CitrusTest
    public void testKafkaUiLogin() {

        $(http().client(kafkaUiAuthLocal)
                .send()
                .post("/api/auth/login")
                .message()
                .type(MessageType.JSON)
                .contentType("application/json")
                .body("{\n" +
                        "  \"username\": \"admin\"\n" +
                        "}")
                .build());

        $(http().client(kafkaUiAuthLocal)
                .receive()
                .response(HttpStatus.BAD_REQUEST)
                .message()
                .type(MessageType.JSON)
                .validate(jsonPath()
                        .expression("$.error", "username and password are required"))
                .build());


    }
    private void saveTokenToProperties(String tokenValue) {
        try {
            Path path = Path.of(
                    "C:\\Users\\sbantzis\\internship\\src\\main\\resources\\tokens.properties"
            );

            Properties props = new Properties();
            if (Files.exists(path)) {
                try (var in = Files.newInputStream(path)) {
                    props.load(in);
                }
            }

            props.setProperty("kafkaUiToken", tokenValue);

            try (OutputStream out = Files.newOutputStream(path)) {
                props.store(out, "Kafka UI auth tokens");
            }

            log.info("Saved kafkaUiToken to tokens.properties");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save token to tokens.properties", e);
        }
    }
}
