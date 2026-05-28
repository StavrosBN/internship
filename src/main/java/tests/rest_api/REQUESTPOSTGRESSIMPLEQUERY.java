package tests.rest_api;

import lombok.extern.slf4j.Slf4j;
import org.citrusframework.annotations.CitrusResource;
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.context.TestContext;


import org.citrusframework.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;

import org.testng.annotations.Test;

import javax.sql.DataSource;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.citrusframework.actions.ExecuteSQLAction.Builder.sql;


@Slf4j
public class REQUESTPOSTGRESSIMPLEQUERY extends TestNGCitrusSpringSupport {

    @Autowired
    private DataSource postgreSqlDataSource;

    @CitrusResource
    private TestContext context;

    @Test
    @CitrusTest
    public void testSelectOne() {
        // Execute: SELECT 1 AS result
        // Note: Exact validation/extraction API can differ slightly by module version.
        // This version uses Citrus SQL query action.
        $(sql()
                .dataSource(postgreSqlDataSource)
                .query()
                .statement("SELECT id FROM public.message_history WHERE created_at >= now() - interval '60 minute' ORDER BY created_at DESC LIMIT 1;")
                .extract("id", "id")
                .build());

        String lastId = context.getVariable("id", String.class);
    savelastId(lastId);
    }
    
    private void savelastId(String idValue){
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

            props.setProperty("message_id", idValue);

            try (OutputStream out = Files.newOutputStream(path)) {
                props.store(out, "PGADMIN ID");
            }

            log.info("Saved the last message Id to tokens.properties", idValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save last message Id to tokens.properties", e);
        }
    }
}

















