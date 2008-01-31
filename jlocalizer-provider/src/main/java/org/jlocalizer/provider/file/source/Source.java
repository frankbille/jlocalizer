package org.jlocalizer.provider.file.source;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jlocalizer.provider.file.File;

public interface Source extends Serializable {

	void configure(Map<String, String> configuration) throws SourceException;

	List<File> listFiles();

	List<File> listFiles(File directory);

	InputStream loadFile(File file) throws SourceException;

	String serializeLocalChanges();

	boolean hasChanged();

}
