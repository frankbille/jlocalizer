package org.jlocalizer.provider;

import org.jlocalizer.backend.domain.ProjectProvider;

public abstract class ProviderFactory {

	public static Provider createProvider(ProjectProvider projectProvider)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Provider provider = null;

		String providerClassName = projectProvider.getProviderClass();
		String configuration = projectProvider.getConfiguration();

		final Class<? extends Provider> providerClass = createProviderClass(providerClassName);

		provider = providerClass.newInstance();
		provider.configure(configuration);

		return provider;
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Provider> createProviderClass(
			String providerClassName) throws ClassNotFoundException {
		Class<? extends Provider> providerClass = null;

		providerClass = (Class<? extends Provider>) Class
				.forName(providerClassName);

		return providerClass;
	}

	private ProviderFactory() {
	}

}
