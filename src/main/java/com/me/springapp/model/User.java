package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    public static final boolean USER_ACTIVE = true;
    public static final boolean USER_DISABLED = false;

    @NotBlank
    @Size(max = 128)
    @Getter
    @Setter
    private String username;

    @NotBlank
    @Size(max = 128)
    @Email
    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Size(max = 128)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean active;

    @Getter
    @Setter
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private Set<Article> articles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Role> roles;

    public User(String username, String email, String password, boolean active, LocalDateTime dateCreated, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.dateCreated = dateCreated;
        this.roles = roles;
    }

    @Override
    public Integer getId() {
        return id();
    }
}
