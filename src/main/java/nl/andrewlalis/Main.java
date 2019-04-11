package nl.andrewlalis;

import nl.andrewlalis.io.FileUtils;
import nl.andrewlalis.model.Credentials;
import nl.andrewlalis.model.RepositoriesList;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;

import java.io.IOException;

/**
 * The main program entry point for this application.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Credentials credentials = FileUtils.readCredentials();
        System.out.println("Credentials: " + credentials);

        if (credentials == null) {
            System.err.println("Could not obtain credentials.");
            System.exit(-1);
        }

        GitHub github;
        if (credentials.isOAuth()) {
            github = GitHub.connectUsingOAuth(credentials.getToken());
        } else {
            github = GitHub.connectUsingPassword(credentials.getUsername(), credentials.getPassword());
        }

        RepositoriesList repositories = FileUtils.readRepositoryNames();

        if (repositories == null) {
            System.err.println("Could not obtain the list of repositories.");
            System.exit(-1);
        }

        GHOrganization org = github.getOrganization(repositories.getOrganizationName());

        System.out.println("Checking for issues...");

        for (String repositoryName : repositories.getRepositoryNames()) {
            new Thread(new IssueChecker(org, repositoryName)).start();
        }
    }

}
