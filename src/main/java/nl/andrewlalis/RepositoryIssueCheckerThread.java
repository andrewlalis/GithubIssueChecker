package nl.andrewlalis;

import nl.andrewlalis.model.Comment;
import nl.andrewlalis.model.Issue;
import nl.andrewlalis.model.IssueType;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Checks if a repository has issues, and prints them if any are found.
 */
public class RepositoryIssueCheckerThread extends Thread {

    private GHOrganization organization;
    private String repositoryName;

    private List<Issue> issues;

    RepositoryIssueCheckerThread(GHOrganization organization, String repositoryName) {
        this.organization = organization;
        this.repositoryName = repositoryName;
        this.issues = new ArrayList<>();
    }

    public boolean hasIssues() {
        return !this.issues.isEmpty();
    }

    public List<Issue> getIssues() {
        return this.issues;
    }

    public List<Issue> getIssues(IssueType type) {
        if (type == IssueType.ALL) {
            return this.getIssues();
        }

        List<Issue> filteredIssues = new ArrayList<>();
        for (Issue issue : this.getIssues()) {
            if (issue.getType().equals(type)) {
                filteredIssues.add(issue);
            }
        }
        return filteredIssues;
    }

    @Override
    public void run() {
        try {
            GHRepository repository = this.organization.getRepository(this.repositoryName);
            if (repository == null) {
                System.err.println("Could not get repository " + this.repositoryName + " from organization " + this.organization.getName() + ". Consider removing it from repositories.txt.");
                return;
            }

            List<GHIssue> issues = repository.getIssues(GHIssueState.OPEN);

            for (GHIssue issue : issues) {
                IssueType type = IssueType.ISSUE;
                if (issue.getPullRequest() != null) {
                    type = IssueType.PULL_REQUEST;
                }
                Issue generatedIssue = new Issue(type, issue.getTitle(), issue.getBody(), issue.getCreatedAt(), this.repositoryName);

                for (GHIssueComment comment : issue.getComments()) {
                    generatedIssue.addComment(new Comment(comment.getUser().getName(), comment.getBody(), comment.getCreatedAt()));
                }

                this.issues.add(generatedIssue);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
