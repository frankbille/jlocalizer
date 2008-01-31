package org.jlocalizer.provider.file.source.mock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.source.AbstractSource;
import org.jlocalizer.provider.file.source.SourceException;

public class MockSource extends AbstractSource {
	private static final long serialVersionUID = 1L;

	private final List<File> files;

	public MockSource() {
		this.files = new ArrayList<File>();
	}

	public void addFile(MockFile file) {
		files.add(file);
	}

	public void configure(Map<String, String> configuration)
			throws SourceException {

	}

	public List<File> listFiles() {
		return files;
	}

	public List<File> listFiles(File directory) {
		MockFile mockDirectory = (MockFile) directory;

		java.io.File directoryFile = mockDirectory.getFile();
		if (directoryFile.isDirectory() == false) {
			directoryFile = directoryFile.getParentFile();
		}

		List<File> childFiles = new ArrayList<File>();

		for (java.io.File childFile : directoryFile.listFiles()) {
			if (childFile.isFile()) {
				childFiles.add(new MockFile(null, childFile));
			}
		}

		return childFiles;
	}

	public InputStream loadFile(File file) throws SourceException {
		MockFile mockFile = (MockFile) file;

		InputStream is = null;
		try {
			is = new FileInputStream(mockFile.getFile());
		} catch (FileNotFoundException e) {
			throw new SourceException(e);
		}
		return is;
	}

}
