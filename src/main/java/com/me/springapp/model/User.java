package com.me.springapp.model;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true, exclude = {"password"})
@RequiredArgsConstructor
// No args constructor needed fot Hibernate only
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User extends AbstractPersistable<Integer> {
    public final static boolean USER_ACTIVE = true;
    public final static boolean USER_DISABLED = false;

    @Column(name = "user_name", nullable = false, unique = true)
    @NotBlank
    @Size(max = 128)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    @Size(max = 128)
    @Email
    private String email;

    @Column(name = "password")
    @NotBlank
    @Size(max = 256)
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Article> articles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;


    public User(String username, String email, String password, boolean active, LocalDateTime dateCreated,
                Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.dateCreated = dateCreated;
        this.roles = roles;
    }
}
