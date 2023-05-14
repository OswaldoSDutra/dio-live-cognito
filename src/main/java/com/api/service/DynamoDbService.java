package com.api.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;

import java.util.Map;
import java.util.Objects;

public class DynamoDbService {

    private AmazonDynamoDB dynamoBuilder;
    private DynamoDB dynamoClient;

    public DynamoDbService() {
        dynamoBuilder = AmazonDynamoDBClientBuilder.defaultClient();
    }

    private DynamoDB getClient(){
        if(dynamoClient == null)
            dynamoClient = new DynamoDB(dynamoBuilder);

        return dynamoClient;
    }

    public Item getItem(String table, String key, int keyValue){
        return this.getClient().getTable(table).getItem(key, keyValue);
    }

    public void putItem(String table, Map<String, Object> dataAttr){

        Item item = new Item();

        for(String attr : dataAttr.keySet()) {
            item.with(attr, dataAttr.get(attr));
        }

        this.getClient().getTable(table)
                .putItem(
                        new PutItemSpec().withItem(
                                item
                        )
                );
    }

}
