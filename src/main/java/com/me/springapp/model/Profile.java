package com.me.springapp.model;

import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

public class Profile {
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @OneToOne
    private User user;
}
