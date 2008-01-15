package org.jlocalizer.backend.domain;


/**
 * An abstract implementation of DomainObject with Long as system id
 */
public abstract class AbstractDomainObject implements DomainObject {

	private Long systemId;

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

}
