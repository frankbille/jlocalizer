package org.jlocalizer.backend.service;

import org.acegisecurity.annotation.Secured;
import org.jlocalizer.backend.domain.Project;

public interface ProjectService {

	@Secured( { "ROLE_ADMIN" })
	void createProject(Project project);

}
