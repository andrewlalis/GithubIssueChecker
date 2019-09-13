package nl.andrewlalis.model.storage;

import java.io.Serializable;
import java.util.Set;

public class TrackedOrganization implements Serializable {
	/**
	 * The name of this organization, as it appears in Github.com.
	 */
	private String name;

	/**
	 * A short description of the organization, optional.
	 */
	private String description;

	/**
	 * The list of repositories that the user is keeping track of in this organization.
	 */
	private Set<TrackedRepository> repositories;

	/**
	 * @return The name of this organization.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The description for this organization.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Checks if the given object is equal to this one.
	 * @param obj The object to compare to this one.
	 * @return True if the other object is the same TrackedOrganization, or false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrackedOrganization) {
			TrackedOrganization other = (TrackedOrganization) obj;
			return other.getName().equals(this.getName());
		}
		return false;
	}
}
