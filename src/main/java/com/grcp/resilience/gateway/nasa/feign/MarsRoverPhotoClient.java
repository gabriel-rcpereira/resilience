package com.grcp.resilience.gateway.nasa.feign;

import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "${api.external.nasa.name}", url = "${api.external.nasa.source}")
public interface MarsRoverPhotoClient {

    @CircuitBreaker(name = "nasapi", fallbackMethod = "findSpacePhotosCircuitBreakerFallback")
    @Retry(name = "nasapi", fallbackMethod = "findSpacePhotosFeignFallback")
    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=${api.external.nasa.key}")
    MarsRoverResponse findSpacePhotos();

    default MarsRoverResponse findSpacePhotosFeignFallback(FeignException e) {
        return MarsRoverResponse.builder().isError(true).fallbackSource("retry").build();
    }

    default MarsRoverResponse findSpacePhotosCircuitBreakerFallback(CallNotPermittedException e) {
        return MarsRoverResponse.builder().isError(true).fallbackSource("circuitBreaker").build();
    }
}
