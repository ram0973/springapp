package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseModel {

    @NotBlank
    @Size(max = 128)
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 128)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private PasswordCipher cipher;

    public User(ModelState state, LocalDateTime dateCreated, String email, String password,
                Set<Article> articles, Set<Role> roles, PasswordCipher cipher) {
        super(state, dateCreated);
        this.email = email;
        this.password = password;
        this.articles = articles;
        this.roles = roles;
        this.cipher = cipher;
    }
}
