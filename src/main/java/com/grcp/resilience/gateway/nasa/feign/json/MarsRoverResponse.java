package com.grcp.resilience.gateway.nasa.feign.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class MarsRoverResponse {

    @Builder.Default
    private List<MarsRoverPhotoResponse> photos = new ArrayList<>();

    private boolean isError;
    private String fallbackSource;

    public boolean hasPhotos() {
        return Optional.ofNullable(this.photos).isPresent() && !this.photos.isEmpty();
    }
}
