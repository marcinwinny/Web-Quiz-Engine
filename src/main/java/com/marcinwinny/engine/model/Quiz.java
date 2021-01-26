package com.marcinwinny.engine.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    public String title;

    @Column(name = "text")
    @NotBlank(message = "Text is mandatory")
    public String text;

    @Column(name = "options")
    @NotEmpty
    @Size(min = 2)
    public String[] options;

    @Column(name = "answer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int[] answer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    public Long id;

    @Column(name = "createdByUser")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String createdByUser;


    public Quiz() {
    }

    public Quiz(@NotBlank(message = "Title is mandatory") String title,
                @NotBlank(message = "Text is mandatory") String text,
                @NotEmpty @Size(min = 2) String[] options,
                int[] answer) {

        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }


    @Override
    public String toString() {
        return "Quiz{" +
               "title='" + title + '\'' +
               ", text='" + text + '\'' +
               ", options=" + Arrays.toString(options) +
               ", answer=" + Arrays.toString(answer) +
               ", id=" + id +
               ", createdByUser='" + createdByUser + '\'' +
               '}';
    }
}