package org.jlocalizer.provider.ui.web.file.format;

import org.jlocalizer.provider.file.format.Format;
import org.jlocalizer.provider.file.format.KeyValueFormat;

public class KeyValueFormatWebUi implements FormatWebUi {

	public boolean handles(Format format) {
		return format instanceof KeyValueFormat;
	}

}
