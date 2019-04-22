package nl.andrewlalis.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Comment implements Comparable {

    private String author;
    private String body;
    private Date createdAt;

    public Comment(String author, String body, Date createdAt) {
        this.author = author;
        this.body = body;
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Comment) {
            Comment c = (Comment) o;
            return c.compareTo(this.getCreatedAt());
        }
        return -1;
    }
}
