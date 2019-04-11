package nl.andrewlalis.model;

/**
 * Represents a list of repositories in an organization.
 */
public class RepositoriesList {

    private String organizationName;

    private String[] repositoryNames;

    public RepositoriesList(String organizationName, String[] repositoryNames) {
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
