package com.revature.put_sets.models;
import lombok.Data;

import java.util.List;


@Data
public class SetDto {

    private String setName;
    private List<Tag> tags;
    private boolean isPublic;

}