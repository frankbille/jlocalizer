package org.jlocalizer.provider.file.source;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jlocalizer.provider.file.File;

public interface Source<F extends File> extends Serializable {

	void configure(Map<String, String> configuration) throws SourceException;

	List<F> listFiles();

	List<F> listFiles(F directory);

	InputStream loadFile(F file) throws SourceException;

	String serializeLocalChanges();

	boolean hasChanged();

}
