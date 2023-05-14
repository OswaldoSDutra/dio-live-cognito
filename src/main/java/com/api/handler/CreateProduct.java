package com.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.api.config.DataBaseTable;
import com.api.message.ApiGatewayResponse;
import com.api.model.Product;
import com.api.service.DynamoDbService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.lang.model.type.ReferenceType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateProduct implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    ObjectMapper mapper;
    Product product;

    @Override
    public ApiGatewayResponse handleRequest (Map<String, Object> input, Context context) {

        DynamoDbService dynamoDbService = new DynamoDbService();
        mapper = new ObjectMapper();

        product = new Product();
        try {

            JsonNode body = mapper.readTree(input.get("body").toString());

            product.setId(UUID.randomUUID().toString().replace("-", ""));
            product.setName(body.get("name").toString());
            product.setPrice(body.get("price").toString());

            Map<String, Object> productMap = mapper.convertValue(product, new TypeReference<Map<String, Object>>() {});

            dynamoDbService.putItem(DataBaseTable.PRODUCT_TABLE.toString(), productMap);

        } catch (Exception ex) {
            logger.error("Error in saving product: " + ex.getMessage());
        }

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(product)
                .build();
    }
}
