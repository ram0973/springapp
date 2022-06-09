package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article extends BaseModel {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String excerpt;

    private String content;

    private String image;

    @ManyToOne
    @JsonIgnoreProperties({"email", "password", "active", "dateCreated", "roles"})
    @JsonInclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "articles_tags",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<ArticleTag> tags;

    @Override
    public Integer getId() {
        return id();
    }

    public Article(ModelState state, LocalDateTime dateCreated, String title, String excerpt, String content,
                   String image, User user, Set<ArticleTag> tags) {
        super(state, dateCreated);
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.image = image;
        this.user = user;
        this.tags = tags;
    }
}
