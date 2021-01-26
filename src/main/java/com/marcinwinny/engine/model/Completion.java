package com.marcinwinny.engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long autoId;

    private Long id;
    private String completedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String completedByUser;

    public Completion(Long quizId, String completedByUser) {
        this.id = quizId;
        this.completedAt = String.valueOf(LocalDateTime.now());
        this.completedByUser = completedByUser;
    }

    public Completion() {
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedByUser() {
        return completedByUser;
    }

    public void setCompletedByUser(String completedByUser) {
        this.completedByUser = completedByUser;
    }
}
