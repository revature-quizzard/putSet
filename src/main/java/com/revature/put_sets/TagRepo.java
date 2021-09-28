package com.revature.put_sets;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.revature.put_sets.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagRepo {

    private final DynamoDBMapper dbReader;

    public TagRepo() {
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    /**
     * The verifyTags method is used to ensure that the list of Tags in the given Set exists in the set of predefined
     * tags in the model.
     * @param tagNames - The list of tag names that belong to the given Set.
     * @return A list of tags from the model whose names matched tagNames.
     */
    public List<Tag> verifyTags(List<String> tagNames) {
        List<Tag> verifiedTags = new ArrayList<>();
        return verifiedTags;
    }

}