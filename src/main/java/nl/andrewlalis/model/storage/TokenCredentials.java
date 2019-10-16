package nl.andrewlalis.model.storage;

import org.kohsuke.github.GitHub;

import java.io.IOException;

/**
 * A type of credential in which an OAuth token is provided.
 */
public class TokenCredentials extends Credentials {
	/**
	 * The token which can be used to log into Github.
	 */
	private String token;

	/**
	 * Constructs a new token credentials object with a token.
	 * @param token The token to use.
	 */
	public TokenCredentials(String token) {
		this.token = token;
	}

	/**
	 * Gets a GitHub object which can be used to retrieve or update information from Github.com.
	 *
	 * @return The authenticated GitHub API object.
	 * @throws IOException If authentication failed, or the API could not be reached.
	 */
	@Override
	public GitHub getAuthenticatedGithubApi() throws IOException {
		return GitHub.connectUsingOAuth(this.token);
	}

	/**
	 * @return The string representation of these credentials.
	 */
	@Override
	public String toString() {
		return "Token: " + this.token;
	}
}
