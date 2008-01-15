package org.jlocalizer.provider.test;

import java.io.IOException;
import java.util.Locale;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.FileBasedProviderConfiguration;
import org.jlocalizer.provider.file.format.KeyValueFormat;
import org.jlocalizer.provider.file.source.Source;
import org.jlocalizer.provider.file.source.SourceException;

public final class MockProviderFactory {

	public static KeyValueFormat createNewKeyValueFormat() {
		KeyValueFormatImpl f = new KeyValueFormatImpl();
		try {
			f.deserialize(null, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return f;
	}

	private static class KeyValueFormatImpl extends KeyValueFormat {
		private static final long serialVersionUID = 1L;

		public void configure(FileBasedProviderConfiguration configuration) {
		}

		public <F extends File> void deserialize(F file, Source<F> source)
				throws IOException, SourceException {
			internalAddEntry(new Locale("da"), "Key", "Danish Value");
			internalAddEntry(new Locale("da", "DK"), "Key",
					"Danish Denmark Value");
			internalAddEntry(new Locale("da", "DK", "Variant"), "Key",
					"A variant of a Danish Denmark Value");
			internalAddEntry(new Locale("en", "DK"), "Key",
					"English Denmark Value");
			internalAddEntry(null, "Key", "Default Value");
			internalAddEntry(null, "Key2", "Default Value 2");
			internalAddEntry(new Locale("da"), "Key2", "Danish Value 2");
		}

		public <F extends File> void serialize(F file, Source<F> source) {
		}

	}

	private MockProviderFactory() {
	}

}
