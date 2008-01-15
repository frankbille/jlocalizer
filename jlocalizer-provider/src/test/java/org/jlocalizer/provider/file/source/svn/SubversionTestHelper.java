package org.jlocalizer.provider.file.source.svn;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public abstract class SubversionTestHelper {

	public static File createSubversionRepository() throws IOException {
		File testRepo = new File("src/test/resources/svn/repo");
		File repo = File.createTempFile("jlocalizer", "svn");
		repo.delete();

		FileUtils.copyDirectory(testRepo, repo);

		return repo;
	}

	public static void deleteSubversionRepository(File subversionRepository)
			throws IOException {
		FileUtils.deleteDirectory(subversionRepository);
	}

	public static String getProject1Files() {
		return "project1/src/main/java/org/jlocalizer/test/resources/Translations.properties"
				+ "=org.jlocalizer.provider.file.format.property.PropertyFileFormat";
	}

	private SubversionTestHelper() {
	}

}
