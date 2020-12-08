package com.grcp.resilience.gateway;

import com.grcp.resilience.core.domain.space.SpacePhoto;
import java.util.Optional;

public interface SpacePhotoGateway {

    Optional<SpacePhoto> findPhotos();
}
