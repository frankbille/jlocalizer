package org.jlocalizer.provider.file.source.svn;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.jlocalizer.provider.file.FileBasedProviderConfiguration;
import org.jlocalizer.provider.file.source.SourceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSubversionSource {

	private File subversionRepository;

	@Before
	public void setup() throws Exception {
		subversionRepository = SubversionTestHelper
				.createSubversionRepository();
	}

	@After
	public void cleanup() throws Exception {
		SubversionTestHelper.deleteSubversionRepository(subversionRepository);
	}

	@Test
	public void testConfigure() throws Exception {
		FileBasedProviderConfiguration configuration = new FileBasedProviderConfiguration();

		configuration.addProperty(SubversionSource.SVN_REPO, "file://"
				+ subversionRepository.getAbsolutePath());
		configuration.addProperty(SubversionSource.SVN_FILES,
				SubversionTestHelper.getProject1Files());

		SubversionSource source = new SubversionSource();
		source.configure(configuration);

		final List<SubversionFile> files = source.listFiles();
		assertThat(files, is(notNullValue()));
		assertThat(files.size(), is(equalTo(1)));

		for (SubversionFile subversionFile : files) {
			assertThat(subversionFile.getFormat(), is(notNullValue()));

			assertThat(subversionFile.getName(), is(notNullValue()));
			assertThat(subversionFile.getName(), is(not(equalTo(""))));
		}
	}

	@Test(expected = SourceException.class)
	public void testConfigureNoSvnRepo() throws Exception {
		FileBasedProviderConfiguration configuration = new FileBasedProviderConfiguration();

		SubversionSource source = new SubversionSource();
		source.configure(configuration);
	}

}
