package org.jlocalizer.frontend.web.components.menu;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class Menu extends Panel {
	private static final long serialVersionUID = 1L;

	public Menu(String id, final List<MenuItem> menuItems) {
		super(id, new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject() {
				return menuItems;
			}
		});
	}

	public Menu(String id, IModel model) {
		super(id, model);

		setRenderBodyOnly(true);

		add(new ListView("menu", model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				MenuItem menuItem = (MenuItem) item.getModelObject();

				final Link link = menuItem.createLink("link");
				item.add(link);

				final Label label = new Label("label", menuItem.getLabel());
				label.setRenderBodyOnly(true);
				link.add(label);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isVisible() {
		return getModelObject() != null
				&& ((List<MenuItem>) getModelObject()).size() > 0;
	}

}
