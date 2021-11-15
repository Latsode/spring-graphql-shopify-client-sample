package com.client.graphql.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class ShopifyHeaderExchangeFilter implements ExchangeFilterFunction {
    @Value("${shopify.access-token}")
    String accessToken;

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest newRequest = ClientRequest.from(request)
                .header("X-Shopify-Storefront-Access-Token", accessToken)
                .build();
        return next.exchange(newRequest);
    }
}
