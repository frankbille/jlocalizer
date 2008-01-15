package org.jlocalizer.provider.file.source;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.FileBasedProviderConfiguration;

public interface Source<F extends File> extends Serializable {

	void configure(FileBasedProviderConfiguration configuration)
			throws SourceException;

	List<F> listFiles();

	List<F> listFiles(F directory);

	InputStream loadFile(F file) throws SourceException;

	String serializeLocalChanges();

	boolean hasChanged();

}
