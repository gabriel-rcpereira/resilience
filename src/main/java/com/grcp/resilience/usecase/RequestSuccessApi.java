package com.grcp.resilience.usecase;

import com.grcp.resilience.domain.dojo.Dojo;
import com.grcp.resilience.gateway.DojoGateway;
import org.springframework.stereotype.Component;

@Component
public class RequestSuccessApi {

    private final DojoGateway dojoGateway;

    public RequestSuccessApi(DojoGateway dojoGateway) {
        this.dojoGateway = dojoGateway;
    }

    public Dojo execute() {
        return dojoGateway.requestSuccess();
    }
}
