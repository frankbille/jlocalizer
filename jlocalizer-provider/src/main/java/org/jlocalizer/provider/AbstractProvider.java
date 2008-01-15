package org.jlocalizer.provider;

import org.jlocalizer.backend.domain.ProviderConfiguration;

public abstract class AbstractProvider<C extends ProviderConfiguration>
		implements Provider<C> {

	private C configuration;

	public C getConfiguration() {
		return configuration;
	}

	public void setConfiguration(C configuration) {
		this.configuration = configuration;
	}

}
