package org.jlocalizer.provider.file.format;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import nanoxml.XMLElement;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.format.KeyValueFormat.Entry;
import org.jlocalizer.provider.file.source.Source;
import org.junit.Test;

public class TestKeyValueFormat {

	private static class KeyValueFormatImpl extends KeyValueFormat {
		private static final long serialVersionUID = 1L;

		public void configure(Map<String, String> configuration) {
		}

		@SuppressWarnings("unchecked")
		public Set<Entry> getInternalEntries() {
			Set<Entry> internalEntries = null;

			try {
				final Field entriesField = KeyValueFormat.class
						.getDeclaredField("entries");
				entriesField.setAccessible(true);
				internalEntries = (Set<Entry>) entriesField.get(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if (internalEntries == null) {
				internalEntries = new HashSet<Entry>();
			}

			return internalEntries;
		}

		public void deserialize(File file, Source source) {
		}

		public void serialize(File file, Source source) {
		}
	}

	@Test
	public void testAddEntry() {
		KeyValueFormatImpl f = new KeyValueFormatImpl();

		assertThat(f.getInternalEntries().size(), equalTo(0));

		f.addEntry(new Locale("da"), "MockKey", "Value 1");
		assertThat(f.getInternalEntries().size(), equalTo(1));

		f.addEntry(new Locale("da"), "MockKey2", "Value 2");
		assertThat(f.getInternalEntries().size(), equalTo(2));

		f.addEntry(new Locale("en"), "MockKey", "Value 3");
		assertThat(f.getInternalEntries().size(), equalTo(3));

		f.addEntry(new Locale("en"), "MockKey2", "Value 4");
		assertThat(f.getInternalEntries().size(), equalTo(4));

		// Default locale
		f.addEntry(null, "MockKey", "Value 5");
		assertThat(f.getInternalEntries().size(), equalTo(5));

		f.addEntry(null, "MockKey2", "Value 6");
		assertThat(f.getInternalEntries().size(), equalTo(6));
	}

	@Test
	public void testGetLocales() {
		KeyValueFormatImpl f = new KeyValueFormatImpl();

		f.addEntry(new Locale("da"), "Key", "Value");
		f.addEntry(new Locale("da", "DK"), "Key", "Value");
		f.addEntry(new Locale("da", "DK", "Variant"), "Key", "Value");
		f.addEntry(new Locale("en", "DK"), "Key", "Value");
		f.addEntry(null, "Key", "Value");
		f.addEntry(null, "Key2", "Value");
		f.addEntry(new Locale("da"), "Key2", "Value");

		final List<Locale> locales = f.getLocales();

		assertThat(locales.size(), is(equalTo(5)));
		assertThat(locales.get(0), is(notNullValue()));
		assertThat(locales.get(0), is(equalTo(new Locale("da"))));
		assertThat(locales.get(1), is(notNullValue()));
		assertThat(locales.get(1), is(equalTo(new Locale("da", "DK"))));
		assertThat(locales.get(2), is(notNullValue()));
		assertThat(locales.get(2),
				is(equalTo(new Locale("da", "DK", "Variant"))));
		assertThat(locales.get(3), is(notNullValue()));
		assertThat(locales.get(3), is(equalTo(new Locale("en", "DK"))));
		assertThat(locales.get(4), is(nullValue()));
	}

	@Test
	public void testCase1() {
		final KeyValueFormat f = createKeyValueFormat();

		assertThat(f.getEntries().size(), equalTo(7));
		for (Entry e : f.getEntries()) {
			assertThat(e.isUnchanged(), is(true));
		}

		Entry entry = f.getEntry(new Locale("da"), "Key");
		entry.setValue("New value");
		assertThat(f.getEntries().size(), equalTo(7));
		for (Entry e : f.getEntries()) {
			if (e == entry) {
				assertThat(e.isEdited(), is(true));
			} else {
				assertThat(e.isUnchanged(), is(true));
			}
		}

		Entry entry2 = f.addEntry(new Locale("de"), "Key", "Ein Value");
		assertThat(f.getEntries().size(), equalTo(8));
		for (Entry e : f.getEntries()) {
			if (e == entry) {
				assertThat(e.isEdited(), is(true));
			} else if (e == entry2) {
				assertThat(e.isAdded(), is(true));
			} else {
				assertThat(e.isUnchanged(), is(true));
			}
		}

		f.removeEntry(new Locale("en", "DK"), "Key");
		assertThat(f.getEntry(new Locale("en", "DK"), "Key"), is(nullValue()));
		assertThat(f.getValue(new Locale("en", "DK"), "Key"), is(nullValue()));
		assertThat(f.getEntries().size(), equalTo(7));
		for (Entry e : f.getEntries()) {
			if (e == entry) {
				assertThat(e.isEdited(), is(true));
			} else if (e == entry2) {
				assertThat(e.isAdded(), is(true));
			} else {
				assertThat(e.isUnchanged(), is(true));
			}
		}

	}

	@Test
	public void testHasChanged() {
		KeyValueFormat f = createKeyValueFormat();
		assertThat(f.hasChanged(), equalTo(false));

		f.addEntry(new Locale("de"), "Key", "Ein value");
		assertThat(f.hasChanged(), equalTo(true));

		f = createKeyValueFormat();
		f.getEntry(new Locale("da"), "Key").setValue("New Value");
		assertThat(f.hasChanged(), equalTo(true));

		f = createKeyValueFormat();
		f.removeEntry(new Locale("en", "DK"), "Key");
		assertThat(f.hasChanged(), equalTo(true));
	}

	@Test
	public void testSerializeLocalChanges() {
		KeyValueFormat f = createKeyValueFormat();
		assertThat(f.serializeLocalChanges(), is(nullValue()));

		f.addEntry(new Locale("de"), "Key", "Ein value");
		String localChanges1 = f.serializeLocalChanges();
		assertThat(localChanges1, is(notNullValue()));

		f.getEntry(new Locale("da"), "Key").setValue("New Value");
		String localChanges2 = f.serializeLocalChanges();
		assertThat(localChanges2, is(notNullValue()));
		assertThat(localChanges2, is(not(equalTo(localChanges1))));

		f.removeEntry(new Locale("en", "DK"), "Key");
		String localChanges3 = f.serializeLocalChanges();
		assertThat(localChanges3, is(notNullValue()));
		assertThat(localChanges3, is(not(equalTo(localChanges2))));
		assertThat(localChanges3, is(not(equalTo(localChanges1))));

		// Now try to change the modified entry back
		f.getEntry(new Locale("da"), "Key").setValue("Value");
		String localChanges4 = f.serializeLocalChanges();
		assertThat(localChanges4, is(notNullValue()));
		assertThat(localChanges4, is(not(equalTo(localChanges3))));
		assertThat(localChanges4, is(not(equalTo(localChanges2))));
		assertThat(localChanges4, is(not(equalTo(localChanges1))));
		// Parse the string to make sure that there is no entry in the stream
		// with this locale and key
		XMLElement element = new XMLElement();
		element.parseString(localChanges4);
		for (Object childObject : element.getChildren()) {
			XMLElement child = (XMLElement) childObject;
			if (child.getAttribute("key").equals("Key")
					&& child.getAttribute("locale").equals("da")) {
				fail("There is still an entry in the serialized output that has locale da and key Key");
			}
		}

		// Now try to remove the newly added entry
		f.removeEntry(new Locale("de"), "Key");
		String localChanges5 = f.serializeLocalChanges();
		assertThat(localChanges5, is(notNullValue()));
		assertThat(localChanges5, is(not(equalTo(localChanges4))));
		assertThat(localChanges5, is(not(equalTo(localChanges3))));
		assertThat(localChanges5, is(not(equalTo(localChanges2))));
		assertThat(localChanges5, is(not(equalTo(localChanges1))));
		// Parse the string to make sure that there is no entry in the stream
		// with this locale and key
		element = new XMLElement();
		element.parseString(localChanges5);
		for (Object childObject : element.getChildren()) {
			XMLElement child = (XMLElement) childObject;
			if (child.getAttribute("key").equals("Key")
					&& child.getAttribute("locale").equals("de")) {
				fail("There is still an entry in the serialized output that has locale de and key Key");
			}
		}
	}

	private KeyValueFormat createKeyValueFormat() {
		KeyValueFormatImpl f = new KeyValueFormatImpl();

		f.internalAddEntry(new Locale("da"), "Key", "Value");
		f.internalAddEntry(new Locale("da", "DK"), "Key", "Value");
		f.internalAddEntry(new Locale("da", "DK", "Variant"), "Key", "Value");
		f.internalAddEntry(new Locale("en", "DK"), "Key", "Value");
		f.internalAddEntry(null, "Key", "Value");
		f.internalAddEntry(null, "Key2", "Value");
		f.internalAddEntry(new Locale("da"), "Key2", "Value");

		return f;
	}

}
