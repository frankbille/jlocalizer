package org.jlocalizer.provider.file.source;

import java.util.List;

import nanoxml.XMLElement;

import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.format.Format;

public abstract class AbstractSource<F extends File> implements Source<F> {

	public boolean hasChanged() {
		boolean hasChanged = false;

		for (F file : listFiles()) {
			if (file.getFormat().hasChanged()) {
				hasChanged = true;
				break;
			}
		}

		return hasChanged;
	}

	public String serializeLocalChanges() {
		XMLElement element = new XMLElement();
		element.setName("source");

		final List<F> files = listFiles();

		for (F file : files) {
			final Format format = file.getFormat();
			String formatChanges = format.serializeLocalChanges();
			if (formatChanges != null) {
				XMLElement fileElement = new XMLElement();
				element.addChild(fileElement);

				fileElement.setName("file");
				fileElement.setAttribute("", "");
			}
		}

		return element.toString();
	}

}
