package org.jlocalizer.provider.file.format.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.format.KeyValueFormat;
import org.jlocalizer.provider.file.source.Source;
import org.jlocalizer.provider.file.source.SourceException;

public class PropertyFileFormat extends KeyValueFormat {
	private static final long serialVersionUID = 1L;

	public static final Pattern BASENAME_PATTERN = Pattern
			.compile("^(.+?)(_([a-zA-Z]{2}))?(_([a-zA-Z]{2}))?(_([\\w]+))?$");

	public void configure(Map<String, String> configuration) {

	}

	public void deserialize(final File file, final Source source)
			throws IOException, SourceException {
		final List<File> directoryFiles = source.listFiles(file);
		final String baseName = getBaseName(file.getName());

		for (File child : directoryFiles) {
			if (isPart(child, baseName)) {
				InputStream inputStream = source.loadFile(child);
				final Properties properties = new Properties();
				properties.load(inputStream);
				inputStream.close();

				// Create Locale
				final String[] childParts = splitName(child.getName());
				Locale locale = null;
				if (childParts[3] != null) {
					locale = new Locale(childParts[1], childParts[2],
							childParts[3]);
				} else if (childParts[2] != null) {
					locale = new Locale(childParts[1], childParts[2]);
				} else if (childParts[1] != null) {
					locale = new Locale(childParts[1]);
				}

				// Add properties to internal cache
				for (Object objectKey : properties.keySet()) {
					String key = objectKey.toString();
					String value = properties.getProperty(key);

					internalAddEntry(locale, key, value);
				}
			}
		}
	}

	public void serialize(final File file, final Source source) {

	}

	protected String getBaseName(String name) {
		String baseName = null;

		final String[] parts = splitName(name);
		if (parts != null) {
			baseName = parts[0];
		}

		return baseName;
	}

	/**
	 * Split the name into its parts:
	 * 
	 * <ol>
	 * <li>Base name
	 * <li>Language
	 * <li>Country
	 * <li>Variant
	 * </ol>
	 * 
	 * @param name
	 *            The name to split
	 * @return The name parts.
	 */
	protected String[] splitName(String name) {
		if (name != null) {
			if (name.indexOf(".properties") > -1) {
				name = name.substring(0, name.indexOf(".properties"));

				final Matcher matcher = BASENAME_PATTERN.matcher(name);
				if (matcher.matches()) {
					return new String[] { matcher.group(1), matcher.group(3),
							matcher.group(5), matcher.group(7) };
				}
			}
		}

		return null;
	}

	protected boolean isPart(File file, String baseName) {
		boolean isPart = false;

		final String childName = file.getName();
		final String[] childParts = splitName(childName);
		if (childParts != null) {
			if (ObjectUtils.equals(baseName, childParts[0])) {
				isPart = true;
			}
		}

		return isPart;
	}

}
