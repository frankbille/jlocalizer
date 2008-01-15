package org.jlocalizer.provider.file.format;

import java.io.IOException;
import java.io.Serializable;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.FileBasedProviderConfiguration;
import org.jlocalizer.provider.file.source.Source;
import org.jlocalizer.provider.file.source.SourceException;

public interface Format extends Serializable {

	void configure(FileBasedProviderConfiguration configuration);

	<F extends File> void deserialize(F file, Source<F> source)
			throws IOException, SourceException;

	<F extends File> void serialize(F file, Source<F> source);

	String serializeLocalChanges();

	boolean hasChanged();

}
