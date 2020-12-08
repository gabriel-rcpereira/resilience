package com.grcp.resilience.gateway;

import com.grcp.resilience.domain.space.SpacePhoto;
import java.util.Optional;

public interface SpacePhotoGateway {

    Optional<SpacePhoto> findPhotos();
}
