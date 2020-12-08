package com.grcp.resilience.entrypoint.rest.json.space;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class SpacePhotoResponse {

    private long id;
    private String imageSource;
    private LocalDate earthDate;
}
