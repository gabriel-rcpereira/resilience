package com.grcp.resilience.entrypoint.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/v1/test")
    public ResponseEntity<Void> getTest() {
        return ResponseEntity.noContent().build();
    }
}
