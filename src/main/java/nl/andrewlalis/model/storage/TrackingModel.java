package nl.andrewlalis.model.storage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains all the information about a single user's tracking information: what organizations they're in, and what
 * repositories they are tracking in that organization.
 */
public class TrackingModel implements Serializable {
	/**
	 * The name of the file that this model is saved to.
	 */
	public static final String FILE_NAME = "tracking_model.obj";

	/**
	 * The credentials used by a user who's tracking some repositories.
	 */
	private Credentials credentials;

	/**
	 * The list of organizations that are being tracked by the user.
	 */
	private Set<TrackedOrganization> organizations;

	/**
	 * Constructs a new empty tracking model without any valid credentials.
	 */
	public TrackingModel() {
		this(null, new HashSet<>());
	}

	/**
	 * Constructs a new tracking model from some provided information.
	 * @param credentials The credentials that the tracking model uses.
	 * @param organizations The organizations tracked by this model.
	 */
	public TrackingModel(Credentials credentials, Set<TrackedOrganization> organizations) {
		this.credentials = credentials;
		this.organizations = organizations;
	}

	/**
	 * @return The list of organizations that are tracked by this model.
	 */
	public Set<TrackedOrganization> getOrganizations() {
		return this.organizations;
	}

	/**
	 * Saves this tracking model to a file.
	 */
	public void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(this);
			oos.flush();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Loads the tracking model from a file.
	 * @return The TrackingModel that has been loaded from the file, or null if it could not be loaded.
	 */
	public static TrackingModel load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			return (TrackingModel) ois.readObject();
		} catch (IOException|ClassNotFoundException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @return A string representation of this model for debugging purposes.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Tracking Model:\n");
		sb.append("Credentials: ").append(this.credentials.toString()).append("\n");
		sb.append("Organizations:\n");
		for (TrackedOrganization organization : this.getOrganizations()) {
			sb.append(organization.toString()).append('\n');
		}
		return sb.toString();
	}
}
