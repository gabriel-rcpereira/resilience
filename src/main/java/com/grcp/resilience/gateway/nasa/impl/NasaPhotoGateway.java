package com.grcp.resilience.gateway.nasa.impl;

import com.grcp.resilience.domain.space.Photo;
import com.grcp.resilience.domain.space.SpacePhoto;
import com.grcp.resilience.gateway.SpacePhotoGateway;
import com.grcp.resilience.gateway.nasa.feign.MarsRoverPhotoClient;
import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverPhotoResponse;
import com.grcp.resilience.gateway.nasa.feign.json.MarsRoverResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NasaPhotoGateway implements SpacePhotoGateway {

    private final MarsRoverPhotoClient marsRoverPhotoClient;

    public NasaPhotoGateway(MarsRoverPhotoClient spacePhoto) {
        this.marsRoverPhotoClient = spacePhoto;
    }

    @Override
    public Optional<SpacePhoto> findPhotos() {
        MarsRoverResponse marsRoverResponse = marsRoverPhotoClient.findSpacePhotos();
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
