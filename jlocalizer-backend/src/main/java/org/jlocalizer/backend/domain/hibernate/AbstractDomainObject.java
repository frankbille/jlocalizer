package org.jlocalizer.backend.domain.hibernate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.jlocalizer.backend.domain.DomainObject;

/**
 * An abstract implementation of DomainObject with Long as system id
 */
@MappedSuperclass
public abstract class AbstractDomainObject implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long systemId;

	public Long getSystemId() {
		return systemId;
	}

}
