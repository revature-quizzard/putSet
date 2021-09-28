package com.revature.put_sets.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.revature.put_sets.models.Set;

public class SetRepo {

    private final DynamoDBMapper dbReader;

    public SetRepo(){
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public void addSet(Set newSet){
        dbReader.save(newSet);
    }

}
