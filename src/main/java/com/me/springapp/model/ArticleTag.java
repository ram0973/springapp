package com.me.springapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags", uniqueConstraints = {@UniqueConstraint(columnNames = "tag")})
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "tags", cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleTag that = (ArticleTag) o;
        return id == that.id && tag.equals(that.tag) && Objects.equals(articles, that.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
