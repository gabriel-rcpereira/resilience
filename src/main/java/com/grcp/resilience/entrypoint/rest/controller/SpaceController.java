package com.grcp.resilience.entrypoint.rest.controller;

import com.grcp.resilience.entrypoint.rest.json.space.SpacePhotoResponse;
import com.grcp.resilience.usecase.FindSpacePhotos;
import com.grcp.resilience.entrypoint.rest.mapper.space.SpacePhotoMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class SpaceController {

    private final FindSpacePhotos findSpacePhotos;
    private final SpacePhotoMapper spacePhotoMapper;

    public SpaceController(FindSpacePhotos findSpacePhotos, SpacePhotoMapper spacePhotoMapper) {
        this.findSpacePhotos = findSpacePhotos;
        this.spacePhotoMapper = spacePhotoMapper;
    }

    @GetMapping("/api/v1/space/photos")
    public ResponseEntity<List<SpacePhotoResponse>> getSpacePhotos() {
        return findSpacePhotos.execute()
                .filter(spacePhoto -> !spacePhoto.isError())
                .map(spacePhotoMapper::mapSpacePhotoResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong."));
    }

}
