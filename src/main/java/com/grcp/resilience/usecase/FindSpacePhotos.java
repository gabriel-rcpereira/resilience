package com.grcp.resilience.usecase;

import com.grcp.resilience.domain.space.SpacePhoto;
import com.grcp.resilience.gateway.SpacePhotoGateway;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FindSpacePhotos {

    private final SpacePhotoGateway spacePhotoGateway;

    public FindSpacePhotos(SpacePhotoGateway spacePhotoGateway) {
        this.spacePhotoGateway = spacePhotoGateway;
    }

    public Optional<SpacePhoto> execute() {
        return spacePhotoGateway.findPhotos();
    }
}
