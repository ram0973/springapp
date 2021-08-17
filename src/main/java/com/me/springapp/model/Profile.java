package com.me.springapp.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class Profile extends BaseModel {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @OneToOne
    private User user;


    protected Profile() {
    }

    @Override
    public Integer getId() {
        return id();
    }
}
