package com.client.graphql;


import com.client.graphql.requests.ShopifyFullRequests;
import com.client.graphql.requests.ShopifyPartialRequests;
import com.client.graphql.util.QueryRootExecutor;
import com.client.graphql.util.ShopifyHeaderExchangeFilter;
import com.graphql_java_generator.client.GraphQLConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication(scanBasePackageClasses = {ClientGraphqlApplication.class, GraphQLConfiguration.class, QueryRootExecutor.class})
public class ClientGraphqlApplication implements CommandLineRunner {

    @Autowired
    private ShopifyPartialRequests shopifyPartialRequests;
    @Autowired
    private ShopifyFullRequests shopifyFullRequests;

    // We need to override this generated Bean so that we can pass our custom ExchangeFilter
    @Bean
    @Primary
    public WebClient webClient(@Qualifier("graphqlEndpoint") String graphqlEndpointAllGraphQLCases,
                               @Autowired(required = false) @Qualifier("httpClientAllGraphQLCases") HttpClient httpClientAllGraphQLCases,
                               @Autowired(required = false) @Qualifier("serverOAuth2AuthorizedClientExchangeFilterFunctionAllGraphQLCases") ServerOAuth2AuthorizedClientExchangeFilterFunction serverOAuth2AuthorizedClientExchangeFilterFunctionAllGraphQLCases,
                               @Autowired ShopifyHeaderExchangeFilter shopifyHeaderExchangeFilter) {


        return GraphQLConfiguration.getWebClient(graphqlEndpointAllGraphQLCases, httpClientAllGraphQLCases,
                serverOAuth2AuthorizedClientExchangeFilterFunctionAllGraphQLCases,
                shopifyHeaderExchangeFilter);
    }


    public static void main(String[] args) {


        SpringApplication.run(ClientGraphqlApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {
        // You can check it out what all these parameters mean at official Shopify StoreFront API documentation
        System.out.println("\n||||| Get First number of collections |||||\n");
        System.out.println(shopifyPartialRequests.getCollections(1,null,null));
        System.out.println("\n||||| Get first number of products in specific collection |||||\n");
        System.out.println(shopifyPartialRequests.getProductsInCollection(null,"nike",50,null,ProductSortKeys.BEST_SELLING,1));
        System.out.println("\n||||| Get first number of products in the store |||||\n");
        System.out.println(shopifyPartialRequests.getProducts(50,null,ProductSortKeys.BEST_SELLING,2,3,5));
        System.out.println("\n||||| Get specific Product |||||\n");
        System.out.println(shopifyPartialRequests.getProductByHandleOrID(null, "racing-shoes", 2, 1, 5));
        System.out.println("\n||||| Just Testing Full Request Should give same result as first query |||||\n");
        System.out.println(shopifyFullRequests.getCollections(1,null,null));

    }
}
