package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags", uniqueConstraints = {@UniqueConstraint(columnNames = "tag")})
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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
    })
    private Set<Article> articles;
}
