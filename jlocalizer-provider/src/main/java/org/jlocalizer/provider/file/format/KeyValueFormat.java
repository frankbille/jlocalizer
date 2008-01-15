package org.jlocalizer.provider.file.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import nanoxml.XMLElement;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jlocalizer.provider.file.format.property.PropertyFileFormat;

/**
 * An abstract implementation of {@link Format}, which helps implementors who
 * are working with a key/value pair based format, where the key is a
 * combination of a {@link Locale} and a string key. An
 * {@link PropertyFileFormat example} of such a format is the java
 * {@link Properties} and {@link ResourceBundle}.
 * <p>
 * To use this class, extend it and implement the
 * {@link Format#serialize(org.jlocalizer.provider.file.File, org.jlocalizer.provider.file.source.Source) serialize}
 * and
 * {@link Format#deserialize(org.jlocalizer.provider.file.File, org.jlocalizer.provider.file.source.Source) deserialize}
 * methods, to extract the information out of the specific format and convert it
 * into the key/value pair.
 */
public abstract class KeyValueFormat implements Format {

	/**
	 * One pair describing a key, which consists of a {@link Locale} and a
	 * string key, and a string value. It is not possible to create newstatic
	 * instances outside of {@link KeyValueFormat}, but instead use
	 * {@link KeyValueFormat#addEntry(Locale, String, String) addEntry}.
	 */
	public class Entry implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Locale locale;
		private final String key;
		private final String originalValue;
		private String value;
		private boolean deleted;

		/**
		 * Only {@link KeyValueFormat} is allowed to create instances of this
		 * class.
		 */
		private Entry(Locale locale, String key, String originalValue,
				String value) {
			this.locale = locale;
			this.key = key;
			this.originalValue = originalValue;
			this.value = value;
		}

		/**
		 * @return The {@link Locale} part of the key.
		 */
		public Locale getLocale() {
			return locale;
		}

		/**
		 * @return The string part of the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @return The value in the key/value pair.
		 */
		public String getValue() {
			if (ObjectUtils.equals(originalValue, value)) {
				return originalValue;
			} else {
				return value;
			}
		}

		/**
		 * @param value
		 *            Set the value. It is not possible to set a value if the
		 *            entry is deleted.
		 */
		public void setValue(String value) {
			if (isDeleted()) {
				throw new IllegalStateException(
						"Can't update the value when entry is deleted.");
			}

			this.value = value;
		}

		/**
		 * @return Flag describing if the entry is deleted.
		 */
		public boolean isDeleted() {
			return deleted;
		}

		/**
		 * Flag describing if the entry is locally added.
		 * 
		 * @return Flag describing if the entry is locally added.
		 */
		public boolean isAdded() {
			return deleted == false && originalValue == null;
		}

		public boolean isEdited() {
			return deleted == false && isAdded() == false
					&& ObjectUtils.equals(originalValue, value) == false;
		}

		public boolean isUnchanged() {
			return deleted == false && ObjectUtils.equals(originalValue, value);
		}

		private void delete() {
			deleted = true;
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(locale);
			b.append(": ");
			b.append(key);
			b.append("=");
			b.append(isUnchanged() ? originalValue : value);
			b.append(" (");
			b.append("added:").append(isAdded()).append(",");
			b.append("edited:").append(isEdited()).append(",");
			b.append("deleted:").append(isDeleted()).append(",");
			b.append("unchanged:").append(isUnchanged()).append(")");
			return b.toString();
		}
	}

	private final Set<Entry> entries;

	protected KeyValueFormat() {
		entries = new LinkedHashSet<Entry>();
	}

	/**
	 * Internal adding of an entry. This is used to mark that an entry is
	 * straight from the source, meaning that it's unmodified. This is normally
	 * called in the implementors
	 * {@link Format#deserialize(org.jlocalizer.provider.file.File, org.jlocalizer.provider.file.source.Source) deserialize}
	 * method, where it converts the unmodified input from a source into a key
	 * value pair.
	 */
	protected Entry internalAddEntry(Locale locale, String key, String value) {
		checkKey(key);

		Entry entry = getEntry(locale, key);

		if (entry != null) {
			entries.remove(entry);
		} else {
			entry = new Entry(locale, key, value, value);
		}

		entries.add(entry);

		return entry;
	}

	public Entry addEntry(Locale locale, String key, String value) {
		checkKey(key);

		Entry entry = getEntry(locale, key);

		if (entry != null) {
			throw new IllegalArgumentException(
					"There already exists an entry with the locale: '" + locale
							+ "' and key: '" + key + "'");
		}

		entry = new Entry(locale, key, null, value);
		entries.add(entry);

		return entry;
	}

	public boolean hasChanged() {
		boolean hasChanged = false;

		for (Entry entry : entries) {
			if (entry.isUnchanged() == false) {
				hasChanged = true;
				break;
			}
		}

		return hasChanged;
	}

	public String serializeLocalChanges() {
		String localChanges = null;

		if (hasChanged()) {
			XMLElement element = new XMLElement();
			element.setName("keyvalue");

			for (Entry entry : entries) {
				if (entry.isUnchanged() == false) {
					XMLElement entryElement = new XMLElement();
					element.addChild(entryElement);

					entryElement.setName("entry");
					entryElement.setAttribute("locale", entry.getLocale()
							.toString());
					entryElement.setAttribute("key", entry.getKey());
					if (entry.isAdded() || entry.isEdited()) {
						entryElement.setContent(entry.getValue());

						if (entry.isAdded()) {
							entryElement.setAttribute("added", "true");
						}
					} else if (entry.isDeleted()) {
						entryElement.setAttribute("deleted", "true");
					} else {
						throw new UnsupportedOperationException(
								"Unknown entry state");
					}
				}
			}

			localChanges = element.toString();
		}

		return localChanges;
	}

	public List<Entry> getEntries() {
		Set<Entry> concatinatedEntries = new LinkedHashSet<Entry>();
		concatinatedEntries.addAll(entries);

		for (Entry entry : entries) {
			if (entry.isDeleted()) {
				concatinatedEntries.remove(entry);
			}
		}

		return new ArrayList<Entry>(concatinatedEntries);
	}

	public List<Entry> getEntries(Locale locale) {
		List<Entry> localeEntries = new ArrayList<Entry>();

		for (Entry entry : getEntries()) {
			if (ObjectUtils.equals(locale, entry.getLocale())) {
				localeEntries.add(entry);
			}
		}

		return localeEntries;
	}

	public List<Entry> getEntries(String key) {
		checkKey(key);

		List<Entry> keyEntries = new ArrayList<Entry>();

		for (Entry entry : getEntries()) {
			if (key.equals(entry.getKey())) {
				keyEntries.add(entry);
			}
		}

		return keyEntries;
	}

	public List<Locale> getLocales() {
		Set<Locale> locales = new LinkedHashSet<Locale>();

		for (Entry entry : getEntries()) {
			locales.add(entry.getLocale());
		}

		return new ArrayList<Locale>(locales);
	}

	public List<String> getKeys() {
		Set<String> keys = new LinkedHashSet<String>();

		for (Entry entry : getEntries()) {
			keys.add(entry.getKey());
		}

		return new ArrayList<String>(keys);
	}

	public Entry getEntry(Locale locale, String key) {
		checkKey(key);

		Entry entry = null;

		for (Entry keyEntry : getEntries(key)) {
			if (ObjectUtils.equals(locale, keyEntry.getLocale())) {
				entry = keyEntry;
				break;
			}
		}

		return entry;
	}

	public String getValue(Locale locale, String key) {
		String value = null;
		Entry entry = getEntry(locale, key);

		if (entry != null) {
			value = entry.getValue();
		}

		return value;
	}

	public void removeEntry(Locale locale, String key) {
		final Entry entry = getEntry(locale, key);

		if (entry != null) {
			if (entry.isEdited() || entry.isUnchanged()) {
				entry.delete();
			} else if (entry.isAdded()) {
				entries.remove(entry);
			}
		}
	}

	private void checkKey(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key must not be null");
		}

		if (key.length() == 0) {
			throw new IllegalArgumentException("Key length must not be 0");
		}
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Entry entry : entries) {
			b.append(entry);
			b.append("\n");
		}
		return b.toString();
	}

}
