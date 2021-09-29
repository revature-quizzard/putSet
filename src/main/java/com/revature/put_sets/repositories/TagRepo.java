package com.revature.put_sets.repositories;

import com.revature.put_sets.models.Tag;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagRepo {

    private final DynamoDbTable<Tag> tagTable;

    public TagRepo() {

        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        tagTable = dbClient.table("Tags", TableSchema.fromBean(Tag.class));

    }

    /**
     * The findTags method is used to ensure that the list of Tags in the given Set exists in the set of predefined
     * tags in the model.
     * @param tagNames - The list of tag names that belong to the given Set.
     * @return A list of tags from the model whose names matched tagNames.
     */
    public List<Tag> findTags(List<String> tagNames) {
        List<Tag> tagQuery = tagNames.stream().map(e -> (new Tag(e))).collect(Collectors.toList());
        List<Tag> result = new ArrayList<>();

        for(Tag t : tagQuery) {
            result.add(tagTable.getItem(t));
        }

        return result;
    }

}