package org.jlocalizer.provider.file;

import org.jlocalizer.provider.AbstractMapConfiguredProvider;
import org.jlocalizer.provider.Provider;
import org.jlocalizer.provider.ProviderException;
import org.jlocalizer.provider.file.format.Format;
import org.jlocalizer.provider.file.source.Source;
import org.jlocalizer.provider.file.source.SourceException;

/**
 * The file based provider is a provider which should be used for all filesystem
 * based translations. This is for example .properties, .po, .xml etc. But it
 * does not cover virtual resources such as databases or web services.
 * 
 * <p>
 * The {@link FileBasedProvider} consists of two important parts:
 * 
 * <ul>
 * <li>{@link Source}, which handles the transport of the file. This could be
 * local file system, FTP, subversion etc.
 * <li>{@link Format}, which describes the actual content of the localised
 * resource. This could be standard java property files and PO files.
 * </ul>
 * 
 * The part about {@link Provider#serializeLocalChanges() serializing} and
 * {@link Provider#deserializeLocalChanges(String) deserializing} local changes
 * is delegated to the {@link Source} and {@link Format} implementations.
 */
public class FileBasedProvider extends AbstractMapConfiguredProvider {
	private static final long serialVersionUID = 1L;

	public static final String SOURCE_CLASS = "SOURCE_CLASS";

	private Source<?> source;

	/**
	 * Get the {@link Source} from the configuration
	 * 
	 * @return The {@link Source} from the configuration
	 * @throws SourceException
	 */
	public Source<?> getSource() throws SourceException {
		if (source == null) {
			source = createSource();
			source.configure(configuration);
		}

		return source;
	}

	protected Source<?> createSource() {
		Source<?> source = null;

		final Class<Source<?>> sourceClass = getSourceClass();

		try {
			source = sourceClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return source;
	}

	public boolean hasChanged() {
		return source.hasChanged();
	}

	public void deserializeLocalChanges(String localChanges)
			throws ProviderException {

	}

	public String serializeLocalChanges() throws ProviderException {
		String localChanges = null;

		try {
			localChanges = getSource().serializeLocalChanges();
		} catch (SourceException e) {
			throw new ProviderException(e);
		}

		return localChanges;
	}

	private String getSourceClassString() {
		return configuration.get(SOURCE_CLASS);
	}

	@SuppressWarnings("unchecked")
	private Class<Source<?>> getSourceClass() {
		Class<Source<?>> sourceClass = null;

		final String sourceClassString = getSourceClassString();

		try {
			sourceClass = (Class<Source<?>>) Class.forName(sourceClassString);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}

		return sourceClass;
	}

}
