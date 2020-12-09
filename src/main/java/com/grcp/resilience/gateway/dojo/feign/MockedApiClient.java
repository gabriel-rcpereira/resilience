package com.grcp.resilience.gateway.dojo.feign;

import com.grcp.resilience.gateway.dojo.json.MockedApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "${api.external.dojo.name}", url = "${api.external.dojo.source}")
public interface MockedApiClient {

    @GetMapping(value = "/test-success", consumes = "application/json")
    MockedApiResponse requestSuccessApi();
}
