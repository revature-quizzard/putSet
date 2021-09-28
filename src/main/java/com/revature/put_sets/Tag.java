package com.revature.put_sets;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Tags")
public class Tag {

    @DynamoDBAttribute
    private String tag_name;

    @DynamoDBAttribute
    private String tag_color;

}
