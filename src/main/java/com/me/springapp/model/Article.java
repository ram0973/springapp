package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "articles")
public class Article {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String excerpt;

    @NotBlank
    private String content;

    private boolean active = false;

    @OneToOne
    @JsonIgnoreProperties({"email", "password", "active"})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Article(String title, String excerpt, String content, boolean active, User user) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.active = active;
        this.user = user;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getContent() {
        return content;
    }

    public boolean isActive() {
        return active;
    }

    public User getUser() {
        return user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
