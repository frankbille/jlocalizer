package org.jlocalizer.frontend.web.components.menu;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;

public class BookmarkableMenuItem extends AbstractMenuItem {
	private static final long serialVersionUID = 1L;

	private final Class<? extends Page> pageClass;
	private final PageParameters pageParameters;

	public BookmarkableMenuItem(String label, Class<? extends Page> pageClass) {
		this(label, pageClass, null);
	}

	public BookmarkableMenuItem(String label, Class<? extends Page> pageClass,
			PageParameters pageParameters) {
		super(label);
		this.pageClass = pageClass;
		this.pageParameters = pageParameters;
	}

	public Link createLink(String wicketId) {
		return new BookmarkablePageLink(wicketId, pageClass, pageParameters);
	}
}
