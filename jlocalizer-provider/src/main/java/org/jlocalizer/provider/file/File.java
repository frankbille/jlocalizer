package org.jlocalizer.provider.file;

import java.io.Serializable;

import org.jlocalizer.provider.file.format.Format;

public interface File extends Serializable {

	String getName();

	Format getFormat();

}
