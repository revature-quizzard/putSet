package com.revature.put_sets.repositories;

import com.revature.put_sets.models.Set;
import com.revature.put_sets.models.SetDto;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * The SetRepository Class is a database repository which provides CRUD operations on the Sets table.
 */
public class SetRepository {

    private final DynamoDbTable<Set> setTable;

    public SetRepository(){
        DynamoDbClient db = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.create())
                .build();

        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(db)
                .build();

        setTable = dbClient.table("Sets", TableSchema.fromBean(Set.class));
    }

    /**
     * The updateSet method targets a Set object in the Sets table using the partition key (id), and updates its fields
     * as provided by the SetDto object passed to it.
     * @param id - The id of the target Set in the Sets table.
     * @param updatedSetDto - The DTO containing updated (or simply eligible for updating operations) fields.
     * @return The updated Set object.
     */
    public Set updateSet(String id, SetDto updatedSetDto) {

        Key key = Key.builder()
                .partitionValue(id)
                .build();

        Set targetSet = setTable.getItem(r -> r.key(key));
        targetSet.setSetName(updatedSetDto.getSetName());
        targetSet.setTags(updatedSetDto.getTags());
        targetSet.setPublic(updatedSetDto.isPublic());

        setTable.updateItem(targetSet);

        return targetSet;

    }

}
