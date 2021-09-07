package com.me.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
// https://stackoverflow.com/a/6084701/548473
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class BaseModel implements Persistable<Integer> {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ModelState state;

    @Getter
    @Setter
    @CreationTimestamp
    private LocalDateTime dateCreated;

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    // https://stackoverflow.com/questions/1638723
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        BaseModel that = (BaseModel) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    public BaseModel(ModelState state, LocalDateTime dateCreated) {
        this.state = state;
        this.dateCreated = dateCreated;
    }
}
