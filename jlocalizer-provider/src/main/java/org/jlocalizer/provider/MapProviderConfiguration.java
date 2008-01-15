package org.jlocalizer.provider;

import java.util.HashMap;
import java.util.Map;

import org.jlocalizer.backend.domain.AbstractDomainObject;
import org.jlocalizer.backend.domain.ProviderConfiguration;

public class MapProviderConfiguration extends AbstractDomainObject implements
		ProviderConfiguration {

	private Map<String, String> properties;

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new HashMap<String, String>();
		}

		return properties;
	}

	public String getProperty(String key) {
		return getProperties().get(key);
	}

	public void addProperty(String key, String value) {
		getProperties().put(key, value);
	}

}
