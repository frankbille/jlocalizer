package org.jlocalizer.backend.domain.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jlocalizer.backend.domain.Project;

@Entity
@Table(name = "projects")
public class HibernateProject extends AbstractDomainObject implements Project {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
