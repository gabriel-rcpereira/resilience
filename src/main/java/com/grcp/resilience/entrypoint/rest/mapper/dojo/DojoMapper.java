package com.grcp.resilience.entrypoint.rest.mapper.dojo;

import com.grcp.resilience.domain.dojo.Dojo;
import com.grcp.resilience.entrypoint.rest.json.dojo.DojoResponse;
import org.springframework.stereotype.Component;

@Component
public class DojoMapper {

    public DojoResponse mapToDojoResponse(Dojo dojo) {
        return DojoResponse.builder()
                .fallbackSource(dojo.getFallbackSource())
                .build();
    }
}
