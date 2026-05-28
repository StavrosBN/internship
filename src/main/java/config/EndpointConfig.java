package config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.citrusframework.dsl.endpoint.CitrusEndpoints;
import org.citrusframework.http.client.HttpClient;
import org.citrusframework.kafka.endpoint.KafkaEndpoint;
import org.citrusframework.kafka.endpoint.KafkaEndpointBuilder;
import org.citrusframework.mail.server.MailServer;
import org.citrusframework.mail.server.MailServerBuilder;
import org.citrusframework.selenium.endpoint.SeleniumBrowser;
import org.citrusframework.selenium.endpoint.SeleniumBrowserBuilder;
import org.citrusframework.variable.GlobalVariables;
import org.citrusframework.variable.GlobalVariablesPropertyLoader;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Map;
import java.util.HashMap;


import javax.net.ssl.SSLContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.UUID;

public class EndpointConfig {

    @Autowired
    private final GlobalVariables globalPropertiesConfig;

    public EndpointConfig(GlobalVariables globalPropertiesConfig) {
        this.globalPropertiesConfig = globalPropertiesConfig;
    }


    @Bean
    public static GlobalVariables globalVariables() {
        return new GlobalVariables();
    }

    @Bean
    @DependsOn("globalVariables")
    public GlobalVariablesPropertyLoader propertyLoader() {
        var propertyLoader = new GlobalVariablesPropertyLoader();
        propertyLoader.getPropertyFiles().add("citrus-application.properties");
        return propertyLoader;
    }



    @Bean
    public MailServer simpleMailServer() {
        return new MailServerBuilder()
                .port(1025)
                .autoStart(true)
                .build();
    }


    @Bean
    public HttpClient kafkaUiAuthLocal() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl("http://localhost:4000")
                .build();
    }


    @Bean
    public DataSource postgreSqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5434/induction");
        dataSource.setUsername("induction");
        dataSource.setPassword("induction");
        return dataSource;
    }



    private static final String AWS_ENV = "aws";
    private static final String SASL_PROTOCOL = "SASL_SSL"; // adjust if needed

    public Map<String, Object> getConsumerProperties() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9094");

        props.put(ConsumerConfig.GROUP_ID_CONFIG,"it-" + UUID.randomUUID());

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"50000");


        //addSaslProperties(props);


        return props;
    }

    private void addSaslProperties(Map<String, Object> props) {
        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");

        var jaasTemplate =
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";

        var jaasCfg = String.format(
                jaasTemplate,"kafka"
        );

        props.put(SaslConfigs.SASL_JAAS_CONFIG, jaasCfg);
        props.put("security.protocol", SASL_PROTOCOL);
    }




    @Bean
    public KafkaEndpoint helloKafkaEndpoint() {
        return new KafkaEndpointBuilder()
                .topic("induction.soap.request.sent")
                .consumerProperties(getConsumerProperties())
                .build();
    }

//    @Bean
//    public KafkaEndpoint helloKafkaEndpoint() {
//        return new KafkaEndpointBuilder()
//                .topic("induction.soap.request.sent")
//                //.server("localhost:8080")
//                //.consumerGroup("citrus_group")
//                .build();
//    }


    @Bean
    public SeleniumBrowser seleniumBrowser() {
        return new SeleniumBrowserBuilder()
                .type("chrome")
                .startPage("http://localhost:5174")
                .build();
    }

    @Bean
    public SeleniumBrowser seleniumBrowser2() {
        return new SeleniumBrowserBuilder()
                .type("chrome")
                .startPage("http://localhost:8080")
                .build();
    }





}
