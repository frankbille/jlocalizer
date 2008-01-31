package org.jlocalizer.frontend.web;

import java.net.MalformedURLException;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.jlocalizer.frontend.web.pages.HomePage;
import org.jlocalizer.frontend.web.pages.ProjectsPage;
import org.jlocalizer.frontend.web.pages.SignInPage;
import org.jlocalizer.service.ProjectService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JLocalizerApplication extends SwarmWebApplication {

	@Override
	protected void init() {
		super.init();

		mountBookmarkablePage("/projects", ProjectsPage.class);
		mountBookmarkablePage("/signin", SignInPage.class);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new JLocalizerSession(request);
	}

	@Override
	public Class<?> getHomePage() {
		return HomePage.class;
	}

	public ProjectService getProjectService() {
		return (ProjectService) getApplicationContext().getBean(
				"projectService");
	}

	private ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	public static JLocalizerApplication get() {
		return (JLocalizerApplication) WebApplication.get();
	}

	@Override
	protected Object getHiveKey() {
		return "jlocalizer";
	}

	@Override
	protected void setUpHive() {
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory();
		try {
			factory.addPolicyFile(getServletContext().getResource(
					"/WEB-INF/jlocalizer.hive"));
		} catch (MalformedURLException e) {
			throw new WicketRuntimeException(e);
		}
		HiveMind.registerHive(getHiveKey(), factory);

	}

	public Class<?> getLoginPage() {
		return SignInPage.class;
	}

}
