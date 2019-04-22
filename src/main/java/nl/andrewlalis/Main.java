package nl.andrewlalis;

import nl.andrewlalis.io.FileUtils;
import nl.andrewlalis.model.Credentials;
import nl.andrewlalis.model.Issue;
import nl.andrewlalis.model.IssueType;
import nl.andrewlalis.model.RepositoriesList;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GitHub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        // Read the list of repositories.
        RepositoriesList repositories = FileUtils.readRepositoryNames();

        if (repositories == null) {
            System.err.println("Could not obtain the list of repositories from repositories.txt");
            System.exit(-1);
        }

        GHOrganization org = github.getOrganization(repositories.getOrganizationName());
        List<RepositoryIssueCheckerThread> repositoryIssueCheckerThreads = new ArrayList<>();

        for (String repositoryName : repositories.getRepositoryNames()) {
            RepositoryIssueCheckerThread checker = new RepositoryIssueCheckerThread(org, repositoryName);
            repositoryIssueCheckerThreads.add(checker);
            checker.start();
        }

        // Wait for all checkers to finish.
        for (RepositoryIssueCheckerThread checkerThread : repositoryIssueCheckerThreads) {
            try {
                checkerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showResults(repositoryIssueCheckerThreads);

    }

    private static void showResults(List<RepositoryIssueCheckerThread> checkerThreads) {
        System.out.println("Done checking for issues. Would you like to see pull requests[1], issues[2], or both[3]?");
        int choice = 3;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid number input, should be 1, 2, or 3.");
            }
        } catch (IOException e) {
            System.err.println("Could not read from input.");
        }

        IssueType filterType;

        switch (choice) {
            case 1:
                System.out.println("Showing pull requests.");
                filterType = IssueType.PULL_REQUEST;
                break;
            case 2:
                System.out.println("Showing issues.");
                filterType = IssueType.ISSUE;
                break;
            default:
                System.out.println("Showing both pull requests and issues.");
                filterType = IssueType.ALL;
        }

        for (RepositoryIssueCheckerThread thread : checkerThreads) {
            for (Issue issue : thread.getIssues(filterType)) {
                System.out.println(issue.toString());
                System.out.println();
            }
        }
    }

}
