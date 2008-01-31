package org.jlocalizer.provider.ui.web.file;

import java.util.List;

import org.jlocalizer.provider.Provider;
import org.jlocalizer.provider.file.FileBasedProvider;
import org.jlocalizer.provider.ui.web.ProviderWebUi;
import org.jlocalizer.provider.ui.web.file.format.FormatWebUi;
import org.jlocalizer.provider.ui.web.file.source.SourceWebUi;

public class FileBasedProviderWebUi implements ProviderWebUi {

	private final List<SourceWebUi> sourceWebUiList;
	private final List<FormatWebUi> formatWebUiList;

	public FileBasedProviderWebUi(List<SourceWebUi> sourceWebUiList,
			List<FormatWebUi> formatWebUiList) {
		this.sourceWebUiList = sourceWebUiList;
		this.formatWebUiList = formatWebUiList;
	}

	public List<FormatWebUi> getFormatWebUiList() {
		return formatWebUiList;
	}

	public List<SourceWebUi> getSourceWebUiList() {
		return sourceWebUiList;
	}

	public boolean handles(Provider provider) {
		return provider instanceof FileBasedProvider;
	}

}
