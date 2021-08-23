package com.me.springapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseModel {

    @Size(max = 100)
    @Getter
    @Setter
    private String firstName;

    @Size(max = 100)
    @Getter
    @Setter
    private String lastName;

    @OneToOne
    @Getter
    @Setter
    private User user;

    @Override
    public Integer getId() {
        return id();
    }
}
