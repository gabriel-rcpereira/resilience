package com.grcp.resilience.core.domain.space;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Photo {

    private long id;
    private String imageSource;
    private LocalDate earthDate;

    private boolean isError;
    private String fallbackSource;
}
