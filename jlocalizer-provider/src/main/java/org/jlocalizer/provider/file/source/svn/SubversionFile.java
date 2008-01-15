package org.jlocalizer.provider.file.source.svn;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.format.Format;
import org.jlocalizer.provider.file.source.SourceException;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class SubversionFile implements File {
	private static final long serialVersionUID = 1L;

	private final SVNRepository repository;
	private final String svnFile;
	private final SVNDirEntry info;
	private final Format format;

	public SubversionFile(SVNRepository repository, String svnFile,
			Format format) throws SourceException {
		this.repository = repository;
		this.svnFile = svnFile;
		this.format = format;

		try {
			info = repository.info(svnFile, SVNRevision.HEAD.getNumber());
		} catch (SVNException e) {
			throw new SourceException(e);
		}
	}

	public String getName() {
		return info.getName();
	}

	public SVNRepository getRepository() {
		return repository;
	}

	public String getSvnFile() {
		return svnFile;
	}

	public Format getFormat() {
		return format;
	}

}
