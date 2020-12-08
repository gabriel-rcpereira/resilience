package com.grcp.resilience.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        WireMockServer wireMock = new WireMockServer(WireMockConfiguration.DYNAMIC_PORT);
        wireMock.start();

        configurableApplicationContext.getBeanFactory().registerSingleton("wireMock", wireMock);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMock.stop();
            }
        });

        String externalDomain = "http://localhost:" + wireMock.port();
        System.setProperty("EXTERNAL_DOMAIN", externalDomain);

//        TestPropertyValues
//                .of("todo_url:" + externalDomain)
//                .applyTo(configurableApplicationContext);
    }
}
