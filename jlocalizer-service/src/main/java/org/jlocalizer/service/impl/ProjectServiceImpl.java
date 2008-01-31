package org.jlocalizer.service.impl;

import org.jlocalizer.backend.dao.ProjectDAO;
import org.jlocalizer.service.ProjectService;

public class ProjectServiceImpl implements ProjectService {

	private final ProjectDAO projectDAO;

	public ProjectServiceImpl(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

}
