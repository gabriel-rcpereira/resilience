package com.grcp.resilience.gateway.dojo.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${api.external.dojo.name}", url = "${api.external.dojo.source}")
public interface MockedApiClient {


}
