package com.me.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
