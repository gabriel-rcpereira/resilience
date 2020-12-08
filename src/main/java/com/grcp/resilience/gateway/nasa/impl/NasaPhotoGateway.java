package com.grcp.resilience.gateway.nasa.impl;

import com.grcp.resilience.core.domain.space.Photo;
import com.grcp.resilience.core.domain.space.SpacePhoto;
import com.grcp.resilience.gateway.SpacePhotoGateway;
import com.grcp.resilience.gateway.nasa.feign.MarsRoverPhoto;
import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverPhotoResponse;
import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NasaPhotoGateway implements SpacePhotoGateway {

    private final MarsRoverPhoto marsRoverPhoto;

    public NasaPhotoGateway(MarsRoverPhoto spacePhoto) {
        this.marsRoverPhoto = spacePhoto;
    }

    @Override
    public Optional<SpacePhoto> findPhotos() {
        MarsRoverResponse marsRoverResponse = marsRoverPhoto.findSpacePhotos();
        return Optional.of(mapToSpacePhotos(marsRoverResponse));
    }

    private SpacePhoto mapToSpacePhotos(MarsRoverResponse marsRoverPhotos) {
        if (marsRoverPhotos.isError()) {
            return mapToSpacePhotoError(marsRoverPhotos);
        }

        return SpacePhoto.builder()
                .photos(mapToPhotos(marsRoverPhotos))
                .build();
    }

    private SpacePhoto mapToSpacePhotoError(MarsRoverResponse marsRoverPhotos) {
        return SpacePhoto.builder()
                .isError(true)
                .errorSource(marsRoverPhotos.getFallbackSource())
                .build();
    }

    private List<Photo> mapToPhotos(MarsRoverResponse marsRoverPhotos) {
        return marsRoverPhotos.getPhotos().stream()
                .map(this::mapToPhoto)
                .collect(Collectors.toList());
    }

    private Photo mapToPhoto(MarsRoverPhotoResponse marsRoverPhoto) {
        return Photo.builder()
                .id(marsRoverPhoto.getId())
                .imageSource(marsRoverPhoto.getImageSource())
                .earthDate(marsRoverPhoto.getEarthDate())
                .build();
    }
}
