package com.thainguyen.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "TODOS")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name must not be null")
    private String name;

    @Min(value = 1, message = "estimate time minimum is 1")
    private Integer estimate_time;

    @Min(value = 1, message = "score minimum is 1")
    @Max(value = 10, message = "score maximum is 10")
    private Integer score;
    private boolean done;

    public ToDo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer namegetEstimate_time() {
        return estimate_time;
    }

    public void setEstimate_time(Integer estimate_time) {
        this.estimate_time = estimate_time;
    }

    public Integer getEstimate_time() {
        return estimate_time;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
