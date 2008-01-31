package org.jlocalizer.frontend.web.pages;

import org.apache.wicket.markup.html.link.Link;
import org.jlocalizer.frontend.web.JLocalizerApplication;
import org.jlocalizer.frontend.web.JLocalizerSession;

public class SignInPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public SignInPage() {
		add(new Link("logIn") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				JLocalizerSession.get().authenticate(null);

				if (continueToOriginalDestination() == false) {
					getRequestCycle().setResponsePage(
							JLocalizerApplication.get().getHomePage());
				}
			}
		});
	}

}
