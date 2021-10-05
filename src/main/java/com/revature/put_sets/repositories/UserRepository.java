package com.revature.put_sets.repositories;

import com.revature.put_sets.models.SetDto;
import com.revature.put_sets.models.Tag;
import com.revature.put_sets.models.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The UserRepository Class is a database repository which provides CRUD operations on the Users table.
 * @author Jack Raney, Alfonso Holmes, and Marwan Bataineh
 */
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(){
        DynamoDbClient db = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.create())
                .build();

        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(db)
                .build();

        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));
    }

    /**
     * Gets all users from the Users table
     * @author Jack Raney and Alfonso Holmes
     */
    public List<User> getAllUsers(){

        return userTable.scan()
                        .items()
                        .stream()
                        .collect(Collectors.toList());

    }

    /**
     * The updateUsersSets method searches for Users from the Users table that have one or more stored Sets as part of
     * their data and updates those Sets with the new information contained within updatedSetDto. This is done to ensure
     * referential integrity between the standalone Sets, and references to them.
     * @param id - The id of the Set that has been updated.
     * @param updatedSetDto - An object containing the updated fields of the Set that was updated.
     */
    public void updateUsersSets(String id, SetDto updatedSetDto, List<Tag> tags) {

        List<User> users = getAllUsers();

        for(User u : users) {

            System.out.println("U: " + u);

            for (User.UserSetDoc set : u.getCreatedSets()) {
                if (set.getId().equals(id)) {
                    set.setSetName(updatedSetDto.getSetName());
                    set.setTags(tags);
                    set.setPublic(updatedSetDto.isPublic());
                }
            }

            for (User.UserSetDoc set : u.getFavoriteSets()) {
                if (set.getId().equals(id)) {
                    set.setSetName(updatedSetDto.getSetName());
                    set.setTags(tags);
                    set.setPublic(updatedSetDto.isPublic());
                }
            }

            userTable.putItem(u);
        }
    }
}