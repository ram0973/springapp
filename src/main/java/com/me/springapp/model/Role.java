package com.me.springapp.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum name;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<User> users;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && name == role.name && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
