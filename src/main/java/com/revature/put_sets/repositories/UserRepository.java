package com.revature.put_sets.repositories;

import com.revature.put_sets.exceptions.ResourceNotFoundException;
import com.revature.put_sets.models.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserRepository {
    private final DynamoDbTable<User> userTable;

    public UserRepository(){
        DynamoDbClient db = DynamoDbClient.builder().httpClient(ApacheHttpClient.create()).build();
        DynamoDbEnhancedClient dbClient = DynamoDbEnhancedClient.builder().dynamoDbClient(db).build();
        userTable = dbClient.table("Users", TableSchema.fromBean(User.class));
    }

    /**
     * Gets all users from the Users table
     * @Authors Jack Raney and Alfonso Holmes
     */
    public List<User> getAllUsers(){ return userTable.scan().items().stream().collect(Collectors.toList()); }

    /**
     * Gets a given user from the Users table by username
     * @Authors Jack Raney and Alfonso Holmes
     * @param name
     */
    public User getUser(String name) {
        AttributeValue val = AttributeValue.builder().s(name).build();
        Expression filter = Expression.builder().expression("#a = :b").putExpressionName("#a", "username") .putExpressionValue(":b", val).build();
        ScanEnhancedRequest request = ScanEnhancedRequest.builder().filterExpression(filter).build();
        User user = userTable.scan(request).stream().findFirst().orElseThrow(ResourceNotFoundException::new).items().get(0);
        System.out.println("USER WITH ID: " + user);
        return user;
    }

    /**
     * Handles referential integrity by removing the given set from all users created_sets and favorited_sets lists
     * @Authors Jack Raney and Alfonso Holmes
     * @param id
     */
    //Jack hates this...
    public void purgeSet(String id) {
        //get rid of set from author's created_sets and all users favorite_sets
        List<User> users = getAllUsers();
        for(User u : users) {
            //Using toKeep to avoid ConcurrentModificationException
            List<User.UserSetDoc> toKeep = new ArrayList<>();
            for (User.UserSetDoc set : u.getCreatedSets()) {
                if (!set.getId().equals(id)) {
                    toKeep.add(set);
                }
            }
            u.setCreatedSets(toKeep);

            //Reset toKeep
            toKeep = new ArrayList<>();
            for (User.UserSetDoc set : u.getFavoriteSets()) {
                if (!set.getId().equals(id)) {
                    toKeep.add(set);
                }
            }
            u.setFavoriteSets(toKeep);
            userTable.putItem(u);
        }
    }
}