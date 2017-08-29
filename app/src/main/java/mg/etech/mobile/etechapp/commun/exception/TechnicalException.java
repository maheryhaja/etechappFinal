package mg.etech.mobile.etechapp.commun.exception;

import java.io.Serializable;
import java.util.UUID;

public class TechnicalException extends RuntimeException implements
		ICommunException, Serializable {

	private static final long serialVersionUID = -3407422302852133179L;
	private static String CODE = "commun.technical";
	private String uuid = UUID.randomUUID().toString();

	public TechnicalException(Throwable t) {
		super(t.getMessage());
	}

	public TechnicalException(String message) {
		super(message);
	}

	public TechnicalException(Throwable t, String uuid) {
		super(t.getMessage());
	}

	public TechnicalException() {
		super();
	}

	public String getCode() {
		return CODE;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

}
