package com.client.graphql.requests;

import com.client.graphql.*;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;


public interface IRequests {

    public CollectionConnection getCollections(Integer first, String after, CollectionSortKeys collectionSortKeys) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException;

    public Collection getProductsInCollection(String id, String handle, Integer firstProducts, String afterProduct, ProductSortKeys productSortKeys, Integer firstImages) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException;

    public ProductConnection getProducts(Integer first, String after, ProductSortKeys productSortKeys, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException;

    public Product getProductByHandleOrID(String id, String handle, Integer firstImages, Integer firstVariants, Integer firstCollections) throws GraphQLRequestPreparationException, GraphQLRequestExecutionException;

}
