package com.revature.put_sets.repositories;

import com.revature.put_sets.models.Set;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

public class SetRepo {

    private final DynamoDbTable<Set> setTable;

    public SetRepo(){
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        setTable = dbClient.table("Sets", TableSchema.fromBean(Set.class));
    }

    public List<Set> getSets() {
        return setTable.scan()
                       .items()
                       .stream()
                       .collect(Collectors.toList());
    }

}
