package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Article extends BaseModel {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String excerpt;

    private String content;

    private String image;

    private boolean active = false;

    private LocalDateTime dateCreated;

    @ManyToOne
    //@JsonIgnoreProperties({"email", "password", "active"})
    @JsonInclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "articles_tags",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<ArticleTag> tags;

    public Article(String title, String excerpt, String content, String image, boolean active,
                   LocalDateTime dateCreated, User user) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.image = image;
        this.active = active;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    protected Article() {
    }

    // Getters/Setters


    public Integer getId() {
        return id();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ArticleTag> getTags() {
        return tags;
    }

    public void setTags(Set<ArticleTag> tags) {
        this.tags = tags;
    }
}
