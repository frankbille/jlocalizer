package org.jlocalizer.provider.file.source.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jlocalizer.provider.file.FileBasedProviderConfiguration;
import org.jlocalizer.provider.file.source.AbstractSource;
import org.jlocalizer.provider.file.source.SourceException;

public class MockSource extends AbstractSource<MockFile> {
	private static final long serialVersionUID = 1L;

	private final List<MockFile> files;

	public MockSource() {
		this.files = new ArrayList<MockFile>();
	}

	public void addFile(MockFile file) {
		files.add(file);
	}

	public void configure(FileBasedProviderConfiguration configuration)
			throws SourceException {

	}

	public List<MockFile> listFiles() {
		return files;
	}

	public List<MockFile> listFiles(MockFile directory) {
		File directoryFile = directory.getFile();
		if (directoryFile.isDirectory() == false) {
			directoryFile = directoryFile.getParentFile();
		}

		List<MockFile> childFiles = new ArrayList<MockFile>();

		for (File childFile : directoryFile.listFiles()) {
			if (childFile.isFile()) {
				childFiles.add(new MockFile(null, childFile));
			}
		}

		return childFiles;
	}

	public InputStream loadFile(MockFile file) throws SourceException {
		InputStream is = null;
		try {
			is = new FileInputStream(file.getFile());
		} catch (FileNotFoundException e) {
			throw new SourceException(e);
		}
		return is;
	}

}
