package nl.andrewlalis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Issue {

    private IssueType type;
    private String title;
    private String body;
    private Date createdAt;
    private String repositoryName;

    private List<Comment> comments;

    public Issue(IssueType type, String title, String body, Date createdAt, String repositoryName) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.repositoryName = repositoryName;
        this.comments = new ArrayList<>();
    }

    public IssueType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public boolean hasComments() {
        return !this.comments.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getType().toString() + " for repository " + this.getRepositoryName() + ": \n" +
                "\tTitle: " + this.getTitle() + '\n' +
                "\tCreated At: " + this.getCreatedAt().toString() + '\n' +
                "\tBody:\n" +
                this.getBody() + '\n');
        if (this.hasComments()) {
            sb.append("\tComments:\n");
            List<Comment> comments = this.getComments();
            Collections.sort(comments);
            int index = 1;
            for (Comment comment : this.getComments()) {
                sb.append(index).append(". ").append(comment.getCreatedAt()).append("\n");
                sb.append("\tAuthor: ").append(comment.getAuthor()).append("\n");
                sb.append("\tBody:\n").append(comment.getBody()).append("\n");
            }
        }
        return sb.toString();
    }
}
