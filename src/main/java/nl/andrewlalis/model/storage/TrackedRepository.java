package nl.andrewlalis.model.storage;

/**
 * Represents a single repository entry that is part of an organization's list of tracked repositories.
 */
public class TrackedRepository {
	/**
	 * The name of the repository.
	 */
	private String name;

	/**
	 * The name of the (optional) organization that this repository belongs to.
	 */
	private String organizationName;

	/**
	 * @return The name of this repository.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The name of the organization that this repository belongs to.
	 */
	public String getOrganizationName() {
		return this.organizationName;
	}

	/**
	 * Determines if another object is equal to this one.
	 * @param obj The object to compare to this one.
	 * @return True if the other object represents the same repository, or false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrackedRepository) {
			TrackedRepository other = (TrackedRepository) obj;
			return other.getName().equals(this.getName())
					&& other.getOrganizationName().equals(this.getOrganizationName());
		}
		return false;
	}
}
