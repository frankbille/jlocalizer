package org.jlocalizer.backend.domain;

public interface ProjectProvider extends DomainObject {

	Project getProject();

	void setProject(Project project);

	String getProviderClass();

	void setProviderClass(String providerClassName);

	String getConfiguration();

	void setConfiguration(String configuration);

}
