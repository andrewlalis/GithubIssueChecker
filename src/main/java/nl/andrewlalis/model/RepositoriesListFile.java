package nl.andrewlalis.model;

/**
 * Represents a list of repositories in an organization.
 */
public class RepositoriesListFile {

    private String organizationName;

    private String[] repositoryNames;

    public RepositoriesListFile(String organizationName, String[] repositoryNames) {
        this.organizationName = organizationName;
        this.repositoryNames = repositoryNames;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String[] getRepositoryNames() {
        return repositoryNames;
    }
}
