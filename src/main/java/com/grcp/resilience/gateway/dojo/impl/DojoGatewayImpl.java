package com.grcp.resilience.gateway.dojo.impl;

import com.grcp.resilience.domain.dojo.Dojo;
import com.grcp.resilience.gateway.DojoGateway;
import com.grcp.resilience.gateway.dojo.feign.MockedApiClient;
import com.grcp.resilience.gateway.dojo.feign.json.MockedApiResponse;
import org.springframework.stereotype.Component;

@Component
public class DojoGatewayImpl implements DojoGateway {

    private final MockedApiClient mockedApiCliente;

    public DojoGatewayImpl(MockedApiClient mockedApiCliente) {
        this.mockedApiCliente = mockedApiCliente;
    }

    @Override
    public Dojo requestSuccess() {
        MockedApiResponse mockedApiResponse = mockedApiCliente.requestSuccessApi();
        return Dojo.builder()
                .fallbackSource(mockedApiResponse.getFallbackSource())
                .build();
    }
}
