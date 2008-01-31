package org.jlocalizer.frontend.web;

import java.security.Principal;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class JLocalizerSession extends WebSession {
	private static final long serialVersionUID = 1L;

	private Long authenticatedUserId;

	public JLocalizerSession(Request request) {
		super(request);
	}

	public Long getAuthenticatedUserId() {
		return authenticatedUserId;
	}

	public boolean isAuthenticated() {
		return authenticatedUserId != null;
	}

	public void authenticate(Principal principal) {
		// TODO
		authenticatedUserId = 1L;
	}

	public static JLocalizerSession get() {
		return (JLocalizerSession) WebSession.get();
	}

}
