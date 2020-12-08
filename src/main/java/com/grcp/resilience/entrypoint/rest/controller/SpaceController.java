package com.grcp.resilience.entrypoint.rest.controller;

import com.grcp.resilience.core.domain.space.Photo;
import com.grcp.resilience.core.domain.space.SpacePhoto;
import com.grcp.resilience.entrypoint.rest.json.SpacePhotoResponse;
import com.grcp.resilience.core.usecase.SpacePhotosFinder;
import com.grcp.resilience.entrypoint.rest.mapper.SpacePhotoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class SpaceController {

    private final SpacePhotosFinder spacePhotosFinder;
    private final SpacePhotoMapper spacePhotoMapper;

    public SpaceController(SpacePhotosFinder spacePhotosFinder, SpacePhotoMapper spacePhotoMapper) {
        this.spacePhotosFinder = spacePhotosFinder;
        this.spacePhotoMapper = spacePhotoMapper;
    }

    @GetMapping("/api/v1/space/photos")
    public ResponseEntity<List<SpacePhotoResponse>> getSpacePhotos() {
        return spacePhotosFinder.execute()
                .filter(spacePhoto -> !spacePhoto.isError())
                .map(spacePhotoMapper::mapSpacePhotoResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong."));
    }

}
