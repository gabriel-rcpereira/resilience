package com.grcp.resilience.gateway.dojo.feign.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MockedApiResponse {

    private String fallbackSource;
}
