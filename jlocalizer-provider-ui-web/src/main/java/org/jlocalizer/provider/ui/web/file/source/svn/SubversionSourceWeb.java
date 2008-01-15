package org.jlocalizer.provider.ui.web.file.source.svn;

import java.util.List;

import org.apache.wicket.Component;
import org.jlocalizer.provider.file.source.svn.SubversionFile;
import org.jlocalizer.provider.file.source.svn.SubversionSource;
import org.jlocalizer.provider.ui.web.file.source.SourceWeb;

public class SubversionSourceWeb implements
		SourceWeb<SubversionFile, SubversionSource> {

	public Component createFileList(String wicketId, List<SubversionFile> files) {
		return null;
	}

}
