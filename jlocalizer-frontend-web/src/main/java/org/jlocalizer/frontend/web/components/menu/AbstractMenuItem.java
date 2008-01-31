package org.jlocalizer.frontend.web.components.menu;


abstract class AbstractMenuItem implements MenuItem {

	private final String label;

	public AbstractMenuItem(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
