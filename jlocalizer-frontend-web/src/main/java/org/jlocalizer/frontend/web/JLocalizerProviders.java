package org.jlocalizer.frontend.web;

import java.util.List;

import org.jlocalizer.provider.ui.web.ProviderWebUi;

public class JLocalizerProviders {

	private final List<ProviderWebUi> providerWebList;

	public JLocalizerProviders(List<ProviderWebUi> providerWebList) {
		this.providerWebList = providerWebList;
	}

	public List<ProviderWebUi> getProviderWebList() {
		return providerWebList;
	}

}
