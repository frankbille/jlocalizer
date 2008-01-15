package org.jlocalizer.provider.ui.web.file.source;

import java.util.List;

import org.apache.wicket.Component;
import org.jlocalizer.provider.file.File;
import org.jlocalizer.provider.file.source.Source;

public interface SourceWeb<F extends File, S extends Source<F>> {

	Component createFileList(String wicketId, List<F> files);

}
