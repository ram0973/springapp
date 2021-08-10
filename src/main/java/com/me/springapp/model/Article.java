package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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

    public Article() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id && active == article.active && title.equals(article.title) &&
            Objects.equals(excerpt, article.excerpt) && Objects.equals(content, article.content) &&
            Objects.equals(image, article.image) && Objects.equals(dateCreated, article.dateCreated) &&
            Objects.equals(user, article.user) && Objects.equals(tags, article.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
