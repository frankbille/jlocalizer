package org.jlocalizer.provider;

import java.io.Serializable;

import org.jlocalizer.backend.domain.ProviderConfiguration;

/**
 * 
 * 
 * @param <C>
 */
public interface Provider<C extends ProviderConfiguration> extends Serializable {

	C getConfiguration();

	/**
	 * Returns true if there has been local changes.
	 * 
	 * @return True if there has been local changes. False if not.
	 */
	boolean hasChanged();

	/**
	 * Serialize the changes that has been done locally on the localized
	 * resource. The result should be a string that is then persisted for later
	 * deserializing through {@link #deserializeLocalChanges()}.
	 * 
	 * @return The local changes in a string form.
	 */
	String serializeLocalChanges() throws ProviderException;

	/**
	 * Deserialize a string with local changes to a localized resource. The
	 * input would be the same as returned from the
	 * {@link #serializeLocalChanges()}.
	 */
	void deserializeLocalChanges(String localChanges) throws ProviderException;

}
