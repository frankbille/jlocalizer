package org.jlocalizer.frontend.web.components.menu;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.Link;

public interface MenuItem extends Serializable {

	Link createLink(String wicketId);

	String getLabel();

}
