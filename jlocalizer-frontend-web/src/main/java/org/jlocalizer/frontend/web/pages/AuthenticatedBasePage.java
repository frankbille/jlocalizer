package org.jlocalizer.frontend.web.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.jlocalizer.frontend.web.components.menu.BookmarkableMenuItem;
import org.jlocalizer.frontend.web.components.menu.MenuItem;

public abstract class AuthenticatedBasePage extends BasePage implements
		ISecurePage {

	public AuthenticatedBasePage() {
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	@Override
	public List<MenuItem> getMenuItems() {
		List<MenuItem> items = new ArrayList<MenuItem>();

		items.add(new BookmarkableMenuItem("Projects 1", ProjectsPage.class));
		items.add(new BookmarkableMenuItem("Projects 2", ProjectsPage.class));
		items.add(new BookmarkableMenuItem("Projects 3", ProjectsPage.class));

		return items;
	}

	public ISecurityCheck getSecurityCheck() {
		return SecureComponentHelper.getSecurityCheck(this);
	}

	public boolean isActionAuthorized(String waspAction) {
		return SecureComponentHelper.isActionAuthorized(this, waspAction);
	}

	public boolean isActionAuthorized(WaspAction action) {
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	public boolean isAuthenticated() {
		return SecureComponentHelper.isAuthenticated(this);
	}

	public void setSecurityCheck(ISecurityCheck check) {
		SecureComponentHelper.setSecurityCheck(this, check);
	}

}
