package com.revature.put_sets.models;
import lombok.Data;

import java.util.List;


/**
 * The SetDto class is a DTO which contains the fields within the Set model which can be updated by the Set author. This
 * prevents illegal updates to Set objects.
 */
@Data
public class SetDto {

    private String setName;
    private List<Tag> tags;
    private boolean isPublic;

}