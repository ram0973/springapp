package com.me.springapp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Comment extends BaseModel {

    @NotBlank
    private String body;

    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    private Article article;

    @Override
    public Integer getId() {
        return id();
    }
}
