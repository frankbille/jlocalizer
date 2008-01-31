package org.jlocalizer.backend.domain.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jlocalizer.backend.domain.Project;
import org.jlocalizer.backend.domain.ProjectProvider;

@Entity
@Table(name = "providers")
public class HibernateProjectProvider extends AbstractDomainObject implements
		ProjectProvider {

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = HibernateProject.class)
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "configuration", length = 512000)
	private String configuration;

	@Column(name = "provider_class", length = 255)
	private String providerClass;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getProviderClass() {
		return providerClass;
	}

	public void setProviderClass(String providerClass) {
		this.providerClass = providerClass;
	}

}
