package org.jlocalizer.provider.ui.web.file.source;

import java.util.List;

import org.apache.wicket.Component;
import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.source.Source;

public interface SourceWebUi {

	boolean handles(Source source);

	Component createFileList(String wicketId, List<File> files);

}
