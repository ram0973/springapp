package com.me.springapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseModel {

    @NotBlank
    @Getter
    @Setter
    private String body;

    @Getter
    @Setter
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    @Getter
    @Setter
    private Article article;

    @Override
    public Integer getId() {
        return id();
    }
}
