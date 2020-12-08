package com.grcp.resilience.entrypoint.rest.mapper;

import com.grcp.resilience.domain.space.Photo;
import com.grcp.resilience.domain.space.SpacePhoto;
import com.grcp.resilience.entrypoint.rest.json.SpacePhotoResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SpacePhotoMapper {

    public List<SpacePhotoResponse> mapSpacePhotoResponse(SpacePhoto spacePhoto) {
        return spacePhoto.getPhotos().stream()
                .map(this::mapToSpacePhotoResponse)
                .collect(Collectors.toList());
    }

    private SpacePhotoResponse mapToSpacePhotoResponse(Photo spacePhoto) {
        return SpacePhotoResponse.builder()
                .id(spacePhoto.getId())
                .imageSource(spacePhoto.getImageSource())
                .earthDate(spacePhoto.getEarthDate())
                .build();
    }
}
