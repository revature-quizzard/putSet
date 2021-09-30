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

/**
 * The TagRepository Class is a database repository which provides CRUD operations on the Tags table.
 */
public class TagRepository {

    private final DynamoDbTable<Tag> tagTable;

    public TagRepository() {

        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        tagTable = dbClient.table("Tags", TableSchema.fromBean(Tag.class));

    }

    /**
     * The findTags method retrieves any Tag objects that exist in the DB that have one of the tag names in the tags
     * List as a List of Tag objects.
     * @param tags - The list of tag names that belong to the given Set.
     * @return A list of tags from the model whose names matched tags.
     */
    public List<Tag> findTags(List<Tag> tags) {

        List<Tag> result = new ArrayList<>();

        for(Tag t : tags) {
            Tag foundT = tagTable.getItem(t);
            if(foundT != null) { result.add(foundT); }
        }

        return result;
    }

}