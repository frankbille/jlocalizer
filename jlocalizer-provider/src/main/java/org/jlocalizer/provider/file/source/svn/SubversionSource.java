package org.jlocalizer.provider.file.source.svn;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jlocalizer.provider.file.FileBasedProviderConfiguration;
import org.jlocalizer.provider.file.format.Format;
import org.jlocalizer.provider.file.source.AbstractSource;
import org.jlocalizer.provider.file.source.SourceException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

public class SubversionSource extends AbstractSource<SubversionFile> {
	private static final long serialVersionUID = 1L;

	public static final String SVN_FILES = "SVN_FILES";

	public static final String SVN_REPO = "SVN_REPO";

	private SVNRepository repository;
	private List<SubversionFile> files;

	public void configure(FileBasedProviderConfiguration configuration)
			throws SourceException {
		configureRepository(configuration);

		configureFiles(configuration);
	}

	private void configureRepository(
			FileBasedProviderConfiguration configuration)
			throws SourceException {
		DAVRepositoryFactory.setup();
		FSRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();

		final String svnRepository = configuration.getProperty(SVN_REPO);
		try {
			SVNURL url = SVNURL.parseURIEncoded(svnRepository);
			repository = SVNRepositoryFactory.create(url);
		} catch (SVNException e) {
			throw new SourceException(e);
		}
	}

	private void configureFiles(FileBasedProviderConfiguration configuration)
			throws SourceException {
		final String svnFilesString = configuration.getProperty(SVN_FILES);
		files = new ArrayList<SubversionFile>();
		if (svnFilesString != null) {
			final String[] svnFiles = svnFilesString.split(",");
			for (String svnFile : svnFiles) {
				final String[] fileFormat = svnFile.split("=");
				if (fileFormat.length != 2) {
					throw new IllegalStateException(
							"The files is not formatted correctly: '"
									+ svnFilesString + "'");
				}
				String file = fileFormat[0];
				String formatClassString = fileFormat[1];
				Format format = getFormat(formatClassString);
				files.add(new SubversionFile(repository, file, format));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Class<Format> getFormatClass(String formatClassString) {
		Class<Format> formatClass = null;

		try {
			formatClass = (Class<Format>) Class.forName(formatClassString);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}

		return formatClass;
	}

	private Format getFormat(String formatClassString) {
		Format format = null;

		final Class<Format> formatClass = getFormatClass(formatClassString);

		try {
			format = formatClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return format;
	}

	public List<SubversionFile> listFiles() {
		return files;
	}

	public List<SubversionFile> listFiles(SubversionFile directory) {
		// directory.getRepository().getDir(path, revision,
		// includeCommitMessages, entries)

		return null;
	}

	public InputStream loadFile(SubversionFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
