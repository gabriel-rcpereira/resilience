package com.grcp.resilience.entrypoint.rest.json.dojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class DojoResponse {

    private String fallbackSource;
}
