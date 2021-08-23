package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseModel {

    @NotBlank
    @Size(max = 255)
    @Getter
    @Setter
    private String title;

    @NotBlank
    @Getter
    @Setter
    private String excerpt;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private boolean active = false;

    @Getter
    @Setter
    private LocalDateTime dateCreated;

    @ManyToOne
    @JsonIgnoreProperties({"email", "password", "active", "dateCreated", "roles"})
    @JsonInclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @Getter
    @Setter
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "articles_tags",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Getter
    @Setter
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

    @Override
    public Integer getId() {
        return id();
    }
}
