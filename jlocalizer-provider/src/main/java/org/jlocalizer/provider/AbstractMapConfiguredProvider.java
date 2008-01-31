package org.jlocalizer.provider;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;


import nanoxml.XMLElement;

/**
 * An abstract provider which handles the configuration of it based on a
 * key/value map of strings, which is serialized/de-serialized into XML.
 */
public abstract class AbstractMapConfiguredProvider implements Provider {

	protected Map<String, String> configuration;

	public void configure(String configuration) {
		XMLElement root = new XMLElement();
		root.parseString(configuration);
		if ("map".equalsIgnoreCase(root.getName()) == false) {
			throw new IllegalStateException("Invalid configuration xml: "
					+ configuration);
		}

		this.configuration = new LinkedHashMap<String, String>();

		final Vector<?> entries = root.getChildren();

		if (entries == null) {
			throw new IllegalStateException("Invalid configuration xml: "
					+ configuration);
		}

		for (Object object : entries) {
			XMLElement entry = (XMLElement) object;
			if ("entry".equalsIgnoreCase(entry.getName()) == false) {
				throw new IllegalStateException("Invalid configuration xml: "
						+ configuration);
			}

			final Vector<?> keyValues = entry.getChildren();
			if (keyValues == null) {
				throw new IllegalStateException("Invalid configuration xml: "
						+ configuration);
			}

			String key = null;
			String value = null;
			for (Object object2 : keyValues) {
				XMLElement element = (XMLElement) object2;
				if ("key".equalsIgnoreCase(element.getName())) {
					key = element.getContent();
				} else if ("value".equalsIgnoreCase(element.getName())) {
					value = element.getContent();
				} else {
					throw new IllegalStateException(
							"Invalid configuration xml: " + configuration);
				}
			}

			this.configuration.put(key, value);
		}
	}

	public String getConfiguration() {
		String conf = null;

		if (this.configuration != null && this.configuration.isEmpty() == false) {
			XMLElement map = new XMLElement();
			map.setName("map");
			for (String key : this.configuration.keySet()) {
				String value = this.configuration.get(key);

				XMLElement entry = new XMLElement();
				entry.setName("entry");
				map.addChild(entry);

				XMLElement keyTag = new XMLElement();
				keyTag.setName("key");
				keyTag.setContent(key);
				entry.addChild(keyTag);

				XMLElement valueTag = new XMLElement();
				valueTag.setName("value");
				valueTag.setContent(value);
				entry.addChild(valueTag);
			}

			conf = map.toString();
		}

		return conf;
	}
}
