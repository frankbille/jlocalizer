package org.jlocalizer.frontend.web.pages;

import org.apache.wicket.markup.html.basic.Label;

public class ProjectsPage extends AuthenticatedBasePage {
	private static final long serialVersionUID = 1L;

	public ProjectsPage() {
		add(new Label("label", "Hello world"));
	}

}
