package org.jlocalizer.frontend.web.pages;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage() {
		throw new RestartResponseAtInterceptPageException(ProjectsPage.class);
	}

}
