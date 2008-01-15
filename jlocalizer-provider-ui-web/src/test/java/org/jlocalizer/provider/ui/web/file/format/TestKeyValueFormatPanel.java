package org.jlocalizer.provider.ui.web.file.format;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.tester.TestPanelSource;
import org.apache.wicket.util.tester.WicketTester;
import org.jlocalizer.provider.file.format.KeyValueFormat;
import org.jlocalizer.provider.test.MockProviderFactory;
import org.jlocalizer.provider.ui.web.file.format.KeyValueFormatPanel;
import org.junit.Test;

public class TestKeyValueFormatPanel {

	@Test
	public void testRender() {
		WicketTester tester = new WicketTester();

		Panel panel = tester.startPanel(new TestPanelSource() {
			private static final long serialVersionUID = 1L;

			public Panel getTestPanel(String panelId) {
				KeyValueFormat format = MockProviderFactory
						.createNewKeyValueFormat();

				Locale locale = new Locale("da");

				return new KeyValueFormatPanel(panelId, format, locale);
			}
		});

		assertThat(panel, is(notNullValue()));

		tester.assertComponent(panel.getPageRelativePath(),
				KeyValueFormatPanel.class);
	}

	@Test
	public void testRenderNullLocale() {
		WicketTester tester = new WicketTester();

		Panel panel = tester.startPanel(new TestPanelSource() {
			private static final long serialVersionUID = 1L;

			public Panel getTestPanel(String panelId) {
				KeyValueFormat format = MockProviderFactory
						.createNewKeyValueFormat();

				return new KeyValueFormatPanel(panelId, format, null);
			}
		});

		assertThat(panel, is(notNullValue()));

		tester.assertComponent(panel.getPageRelativePath(),
				KeyValueFormatPanel.class);
	}

}
