package org.jlocalizer.frontend.web.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.jlocalizer.frontend.web.components.menu.Menu;
import org.jlocalizer.frontend.web.components.menu.MenuItem;

public abstract class BasePage extends WebPage {

	public BasePage() {
		add(HeaderContributor.forCss("css/standard.css"));

		// Title
		final String title = "Jlocalizer";
		add(new Label("pageTitle", title));
		add(new Label("title", title));

		// Menu
		add(new Menu("menu", new PropertyModel(this, "menuItems")));
	}

	public List<MenuItem> getMenuItems() {
		return new ArrayList<MenuItem>();
	}

}
