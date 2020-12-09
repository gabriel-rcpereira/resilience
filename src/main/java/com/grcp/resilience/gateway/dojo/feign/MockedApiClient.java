package com.grcp.resilience.gateway.dojo.feign;

import com.grcp.resilience.gateway.dojo.feign.json.MockedApiResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "${api.external.dojo.name}", url = "${api.external.dojo.source}")
public interface MockedApiClient {

    @CircuitBreaker(name = "nasapi", fallbackMethod = "requestSuccessApiCircuitBreakerFallback")
    @Retry(name = "nasapi", fallbackMethod = "requestSuccessApiFeignFallback")
    @GetMapping(value = "/test-success-after-seven-times", consumes = "application/json")
    MockedApiResponse requestSuccessApi();

    default MockedApiResponse requestSuccessApiFeignFallback(FeignException e) {
        return MockedApiResponse.builder().fallbackSource("retry").build();
    }

    default MockedApiResponse requestSuccessApiCircuitBreakerFallback(CallNotPermittedException e) {
        return MockedApiResponse.builder().fallbackSource("circuitBreaker").build();
    }
}
