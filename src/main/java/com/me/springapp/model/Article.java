package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

    private String excerpt;
    @NotBlank

    private String content;

    private String image;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private ArticleState state;

    @ManyToOne
    @JsonIgnoreProperties({"email", "password", "dateCreated", "roles"})
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
}
