package org.jlocalizer.provider.file.format.property;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.jlocalizer.provider.file.source.mock.MockFile;
import org.jlocalizer.provider.file.source.mock.MockSource;
import org.junit.Test;

public class TestPropertyFileFormat {

	private static class PropertyFileFormatImpl extends PropertyFileFormat {
		private static final long serialVersionUID = 1L;

		@Override
		public String getBaseName(String name) {
			return super.getBaseName(name);
		}

		@Override
		public String[] splitName(String name) {
			return super.splitName(name);
		}
	}

	@Test
	public void testGetBaseName() {
		PropertyFileFormatImpl f = new PropertyFileFormatImpl();

		assertThat(f.getBaseName("Translation.properties"),
				is(equalTo("Translation")));
		assertThat(f.getBaseName("Translation.something.properties"),
				is(equalTo("Translation.something")));
		assertThat(f.getBaseName("Translation_en.properties"),
				is(equalTo("Translation")));
		assertThat(f.getBaseName("Translation_en_US.properties"),
				is(equalTo("Translation")));
		assertThat(f.getBaseName("Translation_en_US_WIN.properties"),
				is(equalTo("Translation")));
		assertThat(f
				.getBaseName("Translation_en_US_Traditional_WIN.properties"),
				is(equalTo("Translation")));
	}

	@Test
	public void testSplitName() {
		PropertyFileFormatImpl f = new PropertyFileFormatImpl();

		String[] parts = f
				.splitName("Translation_en_US_Traditional_WIN.properties");
		assertThat(parts, is(notNullValue()));
		assertThat(parts[0], is(equalTo("Translation")));
		assertThat(parts[1], is(equalTo("en")));
		assertThat(parts[2], is(equalTo("US")));
		assertThat(parts[3], is(equalTo("Traditional_WIN")));

		parts = f.splitName("Translation_en_US.properties");
		assertThat(parts, is(notNullValue()));
		assertThat(parts[0], is(equalTo("Translation")));
		assertThat(parts[1], is(equalTo("en")));
		assertThat(parts[2], is(equalTo("US")));
		assertThat(parts[3], is(nullValue()));

		parts = f.splitName("Translation_en.properties");
		assertThat(parts, is(notNullValue()));
		assertThat(parts[0], is(equalTo("Translation")));
		assertThat(parts[1], is(equalTo("en")));
		assertThat(parts[2], is(nullValue()));
		assertThat(parts[3], is(nullValue()));

		parts = f.splitName("Translation.properties");
		assertThat(parts, is(notNullValue()));
		assertThat(parts[0], is(equalTo("Translation")));
		assertThat(parts[1], is(nullValue()));
		assertThat(parts[2], is(nullValue()));
		assertThat(parts[3], is(nullValue()));
	}

	@Test
	public void testDeserialize() throws Exception {
		String mockFile = "src/test/resources/properties/Translation.properties";
		PropertyFileFormat format = new PropertyFileFormat();
		MockFile file = new MockFile(format, new File(mockFile));
		MockSource source = new MockSource();
		source.addFile(file);

		PropertyFileFormatImpl f = new PropertyFileFormatImpl();
		f.deserialize(file, source);

		int localeCount = 3;
		int keyCount = 4;

		assertThat(f.getEntries().size(), is(localeCount * keyCount));

		assertThat(f.getEntries((Locale) null).size(), is(keyCount));
		assertThat(f.getEntries(new Locale("da")).size(), is(keyCount));
		assertThat(f.getEntries(new Locale("da", "DK")).size(), is(keyCount));

		List<String> keys = f.getKeys();
		assertThat(keys.size(), is(keyCount));
		for (String key : keys) {
			assertThat(f.getEntries(key).size(), is(localeCount));
		}
	}
}
