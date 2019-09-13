package nl.andrewlalis.model.storage;

import org.kohsuke.github.GitHub;

import java.io.IOException;

/**
 * A type of credentials which uses a username and password to obtain a temporary access token from Github.
 */
public class UsernameCredentials extends Credentials {
	/**
	 * The Github username that is used to access the repositories.
	 */
	private String username;

	/**
	 * The password used to access the repositories.
	 */
	private String password;

	/**
	 * Constructs a new username credentials object with a username and password.
	 * @param username The username to use.
	 * @param password The password to use.
	 */
	public UsernameCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets a GitHub object which can be used to retrieve or update information from Github.com.
	 * @return The authenticated GitHub API object.
	 * @throws IOException If authentication failed, or the API could not be reached.
	 */
	@Override
	public GitHub getAuthenticatedGithubApi() throws IOException {
		return GitHub.connectUsingPassword(this.username, this.password);
	}

	/**
	 * @return The string representation of these credentials.
	 */
	@Override
	public String toString() {
		return "Username: " + this.username + ", Password: " + this.password;
	}
}
