package nl.andrewlalis.model.storage;

import java.io.Serializable;
import java.util.Set;

/**
 * Contains all the information about a single user's tracking information: what organizations they're in, and what
 * repositories they are tracking in that organization.
 */
public class TrackingModel implements Serializable {
	/**
	 * The credentials used by a user who's tracking some repositories.
	 */
	private Credentials credentials;

	/**
	 * The list of organizations that are being tracked by the user.
	 */
	private Set<TrackedOrganization> organizations;
}
