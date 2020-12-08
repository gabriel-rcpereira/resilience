package com.grcp.resilience.gateway.nasa.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.grcp.resilience.config.WireMockInitializer;
import com.grcp.resilience.domain.space.SpacePhoto;
import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest()
@ActiveProfiles("test")
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class NasaPhotoGatewayIT {

    private static final String MARS_ROVER_API = "/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=test-key";

    @Autowired
    private CircuitBreakerRegistry registry;

    @Autowired
    private WireMockServer wireMock;

    @Autowired
    private NasaPhotoGateway nasaPhotoGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void executeAfterEach() {
        registry.circuitBreaker("nasapi").reset();
        wireMock.resetRequests();
    }

    @Test
    public void shouldReturnOk() {
        mockSuccessApi();
        nasaPhotoGateway.findPhotos();
    }

    @Test
    public void shouldExecuteApiOneTimeAndRetryThreeTimes() {
        int expectedRequests = 4;

        mockServiceUnavailableApi();
        RequestPattern requestPattern = RequestPatternBuilder.allRequests()
                .withUrl(MARS_ROVER_API)
                .build();

        Optional<SpacePhoto> spacePhotoOptional = nasaPhotoGateway.findPhotos();
        int actualRequests = wireMock.countRequestsMatching(requestPattern).getCount();

        Assertions.assertEquals(expectedRequests, actualRequests);
        Assertions.assertTrue(spacePhotoOptional.isPresent());
        Assertions.assertTrue(spacePhotoOptional.get().isError());
        Assertions.assertEquals(spacePhotoOptional.get().getErrorSource(), "retry");
    }

    @Test
    public void shouldExecuteApiAndExpectsFallbackFromCircuitBreaker() {
        int expectedRequests = 7;

        mockServiceUnavailableApi();
        RequestPattern requestPattern = RequestPatternBuilder.allRequests()
                .withUrl(MARS_ROVER_API)
                .build();

        nasaPhotoGateway.findPhotos();
        Optional<SpacePhoto> spacePhotoOptional = nasaPhotoGateway.findPhotos();
        int actualRequests = wireMock.countRequestsMatching(requestPattern).getCount();

        Assertions.assertEquals(expectedRequests, actualRequests);
        Assertions.assertTrue(spacePhotoOptional.isPresent());
        Assertions.assertTrue(spacePhotoOptional.get().isError());
        Assertions.assertEquals(spacePhotoOptional.get().getErrorSource(), "circuitBreaker");
    }

    @Test
    public void shouldExecuteApiAndExpectsCircuitBreakerClosesToCallApiAgain() throws InterruptedException {
        int expectedRequestsUntilCircuitBreakerCloses = 4;
        int expectedRequestsAfterCircuitBreakerCloses = 1;

        mockServiceUnavailableApi();
        RequestPattern requestPattern = RequestPatternBuilder.allRequests()
                .withUrl(MARS_ROVER_API)
                .build();

        Optional<SpacePhoto> spacePhotoOptionalRetryFallback = nasaPhotoGateway.findPhotos();

        int actualRequestsUntilCircuitBreakerCloses = wireMock.countRequestsMatching(requestPattern).getCount();

        Assertions.assertEquals(expectedRequestsUntilCircuitBreakerCloses, actualRequestsUntilCircuitBreakerCloses);
        Assertions.assertTrue(spacePhotoOptionalRetryFallback.isPresent());
        Assertions.assertTrue(spacePhotoOptionalRetryFallback.get().isError());
        Assertions.assertEquals(spacePhotoOptionalRetryFallback.get().getErrorSource(), "retry");

        Thread.sleep(10100);
        wireMock.resetAll();
        mockSuccessApi();
        Optional<SpacePhoto> spacePhotoOptionalSuccess = nasaPhotoGateway.findPhotos();

        int actualRequestsAfterCircuitBreakerCloses = wireMock.countRequestsMatching(requestPattern).getCount();

        Assertions.assertEquals(expectedRequestsAfterCircuitBreakerCloses, actualRequestsAfterCircuitBreakerCloses);
        Assertions.assertTrue(spacePhotoOptionalSuccess.isPresent());
        Assertions.assertFalse(spacePhotoOptionalSuccess.get().isError());
    }

    private void mockServiceUnavailableApi() {
        wireMock.stubFor(WireMock.get(MARS_ROVER_API).willReturn(WireMock.serviceUnavailable()));
    }

    private void mockSuccessApi() {
        MarsRoverResponse marsRoverResponse = MarsRoverResponse.builder().build();
        String asString = null;
        try {
            asString = objectMapper.writeValueAsString(marsRoverResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        wireMock.stubFor(
                WireMock.get("/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=test-key")
                        .willReturn(WireMock.ok()
                                .withHeader("Content-Type", "application/json")
                                .withBody(asString)));
    }
}
