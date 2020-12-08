package com.grcp.resilience.core.usecase;

import com.grcp.resilience.core.domain.space.SpacePhoto;
import com.grcp.resilience.gateway.SpacePhotoGateway;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SpacePhotosFinder {

    private final SpacePhotoGateway spacePhotoGateway;

    public SpacePhotosFinder(SpacePhotoGateway spacePhotoGateway) {
        this.spacePhotoGateway = spacePhotoGateway;
    }

    public Optional<SpacePhoto> execute() {
        return spacePhotoGateway.findPhotos();
    }
}
