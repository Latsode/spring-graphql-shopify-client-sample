package com.client.graphql.requests;

import com.client.graphql.*;
import com.client.graphql.util.GraphQLRequest;
import com.client.graphql.util.QueryRootExecutor;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShopifyPartialRequests implements IRequests {
    @Autowired
    QueryRootExecutor queryRootExecutor;

    /**
     * Function that returns first N collections
     *
     * @param first              how many collections to return in response
     * @param after              will use this in pagination in the future
     * @param collectionSortKeys how do we want returned collections to be sorted
     *
     *   I currently dont need BindValues but in future if I may need to include products in response I can easily
     *   pass products parameters with params map
     */
    @Override
    public CollectionConnection getCollections(Integer first, String after, CollectionSortKeys collectionSortKeys) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {

        GraphQLRequest partialRequest = queryRootExecutor.getCollectionsGraphQLRequest("{edges {node{ id __typename title handle onlineStoreUrl image{ originalSrc } } } } ");
        Map<String, Object> params = new HashMap<>();

        return queryRootExecutor.collectionsWithBindValues(partialRequest, first, after, null, null, null, collectionSortKeys, null, params);
    }


    /**
     * Function that returns all products from specified collection
     *
     * @param id              we can identify collection with id or
     * @param handle          we can identify collection with handle
     * @param firstProducts   how many products to return in collection
     * @param afterProduct    will use this for pagination in the future
     * @param productSortKeys how do we want returned products to be sorted
     * @param firstImages     how many images to show for each product
     */
    @Override
    public Collection getProductsInCollection(String id, String handle, Integer firstProducts, String afterProduct, ProductSortKeys productSortKeys, Integer firstImages) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {

        GraphQLRequest partialRequest = queryRootExecutor.getCollectionGraphQLRequest("{id title handle products(first: ?firstProducts, sortKey: ?productSortKey) { edges { node { id title description handle tags vendor availableForSale images(first: ?firstImages) { edges { node { id originalSrc width height } } } priceRange { minVariantPrice { amount currencyCode } maxVariantPrice { amount currencyCode } } } } } }");
        Map<String, Object> params = new HashMap<>();
        params.put("firstProducts", firstProducts);
        params.put("productSortKey", productSortKeys);
        params.put("firstImages", firstImages);


        return queryRootExecutor.collectionWithBindValues(partialRequest, id, handle, params);
    }

    /**
     * Function that returns N amount of products
     *
     * @param first            how many products we want to be returned
     * @param after            will use this for pagination in the future
     * @param productSortKeys  how do we want returned products to be sorted
     * @param firstImages      how many images to show for each product
     * @param firstVariants    how many variations to show for each product. Variation examples: Small/Medium/Large or colors
     * @param firstCollections each product may belong to multiple collections , with this we specify how many collections to return
     */
    @Override
    public ProductConnection getProducts(Integer first, String after, ProductSortKeys productSortKeys, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        GraphQLRequest partialRequest = queryRootExecutor.getProductsGraphQLRequest("{edges { node { id title description handle tags vendor availableForSale images(first: ?firstImages) { edges { cursor node { id originalSrc width height } } } priceRange { minVariantPrice { amount currencyCode } maxVariantPrice { amount currencyCode } } variants(first: ?firstVariants) { edges { cursor node { id title quantityAvailable priceV2 { amount currencyCode } } } } collections(first : ?firstCollections){ edges{ node{ id __typename title handle onlineStoreUrl image{ originalSrc } } } } } } }");
        Map<String, Object> params = new HashMap<>();
        params.put("firstImages", firstImages);
        params.put("firstVariants", firstVariants);
        params.put("firstCollections", firstCollections);

        return queryRootExecutor.productsWithBindValues(partialRequest, first, after, null, null, null, productSortKeys, null, params);
    }

    /**
     * Function that returns specified product
     *
     * @param id               we can identify product with id or
     * @param handle           we can identify product with handle
     * @param firstImages      how many images to show for product
     * @param firstVariants    how many variants of product to show . Variation examples: Small/Medium/Large or colors
     * @param firstCollections product may belong to multiple collections , with this we specify how many collections to return
     */
    @Override
    public Product getProductByHandleOrID(String id, String handle, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
        GraphQLRequest partialRequest = queryRootExecutor.getProductGraphQLRequest("{ id title description handle tags vendor availableForSale images(first: ?firstImages) { edges { cursor node { id originalSrc width height } } } priceRange { minVariantPrice { amount currencyCode } maxVariantPrice { amount currencyCode } } variants(first: ?firstVariants) { edges { cursor node { id title quantityAvailable priceV2 { amount currencyCode } } } } collections(first : ?firstCollections){ edges{ node{ id __typename title handle onlineStoreUrl image{ originalSrc } } } } }");
        Map<String, Object> params = new HashMap<>();
        params.put("firstImages", firstImages);
        params.put("firstVariants", firstVariants);
        params.put("firstCollections", firstCollections);


        return queryRootExecutor.productWithBindValues(partialRequest, id, handle, params);
    }
}
