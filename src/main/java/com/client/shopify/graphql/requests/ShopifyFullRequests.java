package com.client.shopify.graphql.requests;

import com.client.graphql.*;
import com.client.graphql.util.GraphQLRequest;
import com.client.graphql.util.QueryRootExecutor;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ShopifyFullRequests implements IRequests {
    @Autowired
    QueryRootExecutor queryRootExecutor;

    /**
     * Function that gets first N collections
     * <p>
     * <p>
     * This is a very bad implementation of how to build and use such queries but
     * I did this just to demonstrate that you can use Full Requests
     * for complex queries such as one below which uses fragment
     * I literally just copy pasted this query that I wrote in Altair
     */
    @Override
    public CollectionConnection getCollections(Integer first, String after, CollectionSortKeys collectionSortKeys) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        GraphQLRequest graphQLFullRequest = new GraphQLRequest("query{\n" +
                "\tcollections(first : " + first + "){\n" +
                "    edges{\n" +
                "      \n" +
                "      node{\n" +
                "        id\n" +
                "        __typename\n" +
                "        title\n" +
                "        handle\n" +
                "        onlineStoreUrl\n" +
                "        ...collectionImage\n" +
                "\t\n" +
                " \t\t\t\n" +
                "        \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "fragment collectionImage on Collection{\n" +
                "  image{\n" +
                "    id\n" +
                "    originalSrc\n" +
                "    width\n" +
                "    height\n" +
                "  }\n" +
                "}");
        return queryRootExecutor.execWithBindValues(graphQLFullRequest, null).getCollections();
    }

    @Override
    public Collection getProductsInCollection(String id, String handle, Integer firstProducts, String afterProduct, ProductSortKeys productSortKeys, Integer firstImages) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        return null;
    }

    @Override
    public ProductConnection getProducts(Integer first, String after, ProductSortKeys productSortKeys, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        return null;
    }

    @Override
    public Product getProductByHandleOrID(String id, String handle, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        return null;
    }
}
