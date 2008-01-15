package org.jlocalizer.provider;

public class ProviderException extends Exception {
	private static final long serialVersionUID = 1L;

	public static enum Reason {
		LOCAL_CHANGES_SERIALIZATION_NOT_SUPPORTED
	}

	private final Reason reason;

	public ProviderException() {
		reason = null;
	}

	public ProviderException(Reason reason) {
		this.reason = reason;

	}

	public ProviderException(Throwable cause) {
		super(cause);
		reason = null;
	}

	public ProviderException(Reason reason, Throwable cause) {
		super(cause);
		this.reason = reason;
	}

	public Reason getReason() {
		return reason;
	}

}
