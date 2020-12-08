package com.grcp.resilience.domain.dojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Dojo {

    private String fallbackSource;
}
