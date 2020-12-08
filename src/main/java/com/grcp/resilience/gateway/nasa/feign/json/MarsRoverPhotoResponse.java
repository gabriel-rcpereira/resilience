package com.grcp.resilience.gateway.nasa.feign.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarsRoverPhotoResponse {

    private long id;
    @JsonProperty("img_src")
    private String imageSource;
    @JsonProperty("earth_date")
    private LocalDate earthDate;
}
