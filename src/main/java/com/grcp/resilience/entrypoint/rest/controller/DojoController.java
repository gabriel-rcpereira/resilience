package com.grcp.resilience.entrypoint.rest.controller;

import com.grcp.resilience.domain.dojo.Dojo;
import com.grcp.resilience.entrypoint.rest.json.dojo.DojoResponse;
import com.grcp.resilience.entrypoint.rest.mapper.dojo.DojoMapper;
import com.grcp.resilience.usecase.RequestSuccessApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DojoController {

    private final DojoMapper dojoMapper;
    private final RequestSuccessApi requestSuccessApi;

    public DojoController(DojoMapper dojoMapper, RequestSuccessApi requestSuccessApi) {
        this.dojoMapper = dojoMapper;
        this.requestSuccessApi = requestSuccessApi;
    }

    @GetMapping("/dojo/success")
    public ResponseEntity<DojoResponse> getRequestSuccess() {
        Dojo dojo = requestSuccessApi.execute();
        return ResponseEntity.ok(dojoMapper.mapToDojoResponse(dojo));
    }

}
