package org.jlocalizer.provider.file;

import org.jlocalizer.provider.MapProviderConfiguration;
import org.jlocalizer.provider.file.source.Source;

public class FileBasedProviderConfiguration extends MapProviderConfiguration {

	public static final String SOURCE_CLASS = "SOURCE_CLASS";

	public String getSourceClassString() {
		return getProperty(SOURCE_CLASS);
	}

	@SuppressWarnings("unchecked")
	public Class<Source<?>> getSourceClass() {
		Class<Source<?>> sourceClass = null;

		final String sourceClassString = getSourceClassString();

		try {
			sourceClass = (Class<Source<?>>) Class.forName(sourceClassString);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}

		return sourceClass;
	}

	public Source<?> getSource() {
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

}
