package com.me.springapp.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile extends BaseModel {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @OneToOne
    private User user;

    @Override
    public Integer getId() {
        return id();
    }
}
