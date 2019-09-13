package nl.andrewlalis.model.storage;

import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.io.Serializable;

/**
 * Represents the credentials needed by a user to use the Github API.
 */
public abstract class Credentials implements Serializable {
	/**
	 * Gets a GitHub object which can be used to retrieve or update information from Github.com.
	 * @return The authenticated GitHub API object.
	 * @throws IOException If authentication failed, or the API could not be reached.
	 */
	public abstract GitHub getAuthenticatedGithubApi() throws IOException;
}
