package nl.andrewlalis;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.List;

/**
 * Checks if a repository has issues, and prints them if any are found.
 */
public class IssueChecker implements Runnable {

    private GHOrganization organization;
    private String repositoryName;

    IssueChecker(GHOrganization organization, String repositoryName) {
        this.organization = organization;
        this.repositoryName = repositoryName;
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
                System.out.println(repository.getUrl() + " -> " + issue.getTitle() + "\n" + issue.getBody());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
