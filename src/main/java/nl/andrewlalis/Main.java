package nl.andrewlalis;

import nl.andrewlalis.io.FileUtils;
import nl.andrewlalis.model.Credentials;
import nl.andrewlalis.model.Issue;
import nl.andrewlalis.model.IssueType;
import nl.andrewlalis.model.RepositoriesListFile;
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

        RepositoriesListFile repositories = FileUtils.readRepositoryNames();
        if (repositories == null) {
            System.err.println("Could not obtain the list of repositories from repositories.txt");
            System.exit(-1);
        }

        System.out.println("Fetching issues from all repositories...");
        long start = System.currentTimeMillis();
        List<RepositoryIssueCheckerThread> finishedThreads = fetchIssues(repositories, github);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Done! Finished in " + elapsed + "ms.");
        showResults(finishedThreads);
    }

    /**
     * Generates some threads which each fetches issues from one repository.
     * @param repositoriesListFile The repositories list which contains all the repository names and organization name.
     * @param github The Github API interaction object.
     * @return The list of threads after they've all completed, so that data can be extracted from them.
     */
    private static List<RepositoryIssueCheckerThread> fetchIssues(RepositoriesListFile repositoriesListFile, GitHub github) {
        try {
            GHOrganization org = github.getOrganization(repositoriesListFile.getOrganizationName());
            List<RepositoryIssueCheckerThread> repositoryIssueCheckerThreads = new ArrayList<>();

            for (String repositoryName : repositoriesListFile.getRepositoryNames()) {
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

            return repositoryIssueCheckerThreads;
        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    private static void showResults(List<RepositoryIssueCheckerThread> checkerThreads) {
        System.out.println("Would you like to see pull requests[1], issues[2], or both[3]?");
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
            }
        }
    }

}
