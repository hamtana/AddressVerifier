package com.example.AddressVerifier.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class LinzWfsClient {

    private final WebClient webClient;
    private final String apiKey;
    private final String layer;

    public LinzWfsClient(
            WebClient webClient,
            @Value("${linz.api.api-key}") String apiKey,
            @Value("${linz.api.layer}") String layer) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.layer = layer;
    }

    /**
     * Class will query then API provided by Datalinz NZ
     * @param cqlFilter
     * @return
     */
    public Mono<String> queryByCql(String cqlFilter) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(";key=" + apiKey+ "/wfs")                      // WFS endpoint
                        .queryParam("service", "WFS")
                        .queryParam("version", "2.0.0")
                        .queryParam("request", "GetFeature")
                        .queryParam("typeNames", layer)    // your layer
                        .queryParam("outputFormat", "json")
                        .queryParam("CQL_FILTER", cqlFilter)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }



}