package com.revature.put_sets.models;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class Tag {

    private String tag_name;
    private String tag_color;

    @DynamoDbPartitionKey
    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @DynamoDbAttribute("tag_color")
    public String getTag_color() {
        return tag_color;
    }

    public void setTag_color(String tag_color) {
        this.tag_color = tag_color;
    }

    public Tag(String name){
        this.tag_name = name;
    }

    public Tag() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        return this.tag_name.equals(o);
    }

}
