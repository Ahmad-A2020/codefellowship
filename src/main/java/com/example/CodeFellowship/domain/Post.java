package com.example.CodeFellowship.domain;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name="Post_Owner")
    private ApplicationUser owner;

    public Post() {

    }

    public Post(String body,ApplicationUser owner) {
        this.body = body;
        this.owner=owner;

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body ) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
}
