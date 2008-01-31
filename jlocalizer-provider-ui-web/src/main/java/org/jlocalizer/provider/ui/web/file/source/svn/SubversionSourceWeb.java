package org.jlocalizer.provider.ui.web.file.source.svn;

import java.util.List;

import org.apache.wicket.Component;
import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.source.Source;
import org.jlocalizer.provider.file.source.svn.SubversionSource;
import org.jlocalizer.provider.ui.web.file.source.SourceWebUi;

public class SubversionSourceWeb implements SourceWebUi {

	public Component createFileList(String wicketId, List<File> files) {
		return null;
	}

	public boolean handles(Source source) {
		return source instanceof SubversionSource;
	}

}
