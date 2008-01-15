package org.jlocalizer.provider.ui.web.file.format;

import java.util.Locale;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.jlocalizer.provider.file.format.KeyValueFormat;
import org.jlocalizer.provider.file.format.KeyValueFormat.Entry;

public class KeyValueFormatPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public KeyValueFormatPanel(String id, final KeyValueFormat format,
			final Locale locale) {
		super(id);

		IModel listModel = new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject() {
				return format.getKeys();
			}
		};

		add(new ListView("keys", listModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				final String key = item.getModelObjectAsString();

				// Key column
				item.add(new Label("key", key));

				// Default column
				item.add(new Label("default", format.getValue(null, key)));

				// Locale column
				item.add(new TextField("value", new IModel() {
					private static final long serialVersionUID = 1L;

					private Entry entry;

					{
						entry = format.getEntry(locale, key);
						if (entry == null) {
							entry = format.addEntry(locale, key, null);
						}
					}

					public Object getObject() {
						return entry.getValue();
					}

					public void setObject(Object object) {
						String value = (String) object;
						entry.setValue(value);
					}

					public void detach() {
						// No detach possible
					}
				}));
			}
		});
	}

}
