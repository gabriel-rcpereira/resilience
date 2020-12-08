package com.grcp.resilience.core.domain.space;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class SpacePhoto {

    private boolean isError;
    private String errorSource;

    @Builder.Default
    private List<Photo> photos = new ArrayList<>();
}
