package com.me.springapp.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags", uniqueConstraints = {@UniqueConstraint(columnNames = "tag")})
public class ArticleTag extends BaseModel {

    @NotBlank
    private String tag;

    @ManyToMany(mappedBy = "tags", cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @Override
    public Integer getId() {
        return id();
    }

}
