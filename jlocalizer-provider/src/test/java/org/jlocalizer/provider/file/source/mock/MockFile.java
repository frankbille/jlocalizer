package org.jlocalizer.provider.file.source.mock;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.format.Format;

public class MockFile implements File {
	private static final long serialVersionUID = 1L;

	private Format format;
	private java.io.File file;

	public MockFile() {
	}

	public MockFile(Format format, java.io.File file) {
		this.format = format;
		this.file = file;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public String getName() {
		return file.getName();
	}

	public java.io.File getFile() {
		return file;
	}

}
