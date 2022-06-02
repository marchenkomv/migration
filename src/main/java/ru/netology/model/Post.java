package ru.netology.model;

public class Post {
    private long id;
    private String content;
    private transient boolean isRemoved;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
        this.isRemoved = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}