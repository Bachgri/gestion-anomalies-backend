package insight.api.anomalies.exception;

import javax.security.sasl.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public JwtTokenMissingException(String msg) {
		//super(msg);
		System.err.println("Erreur:No JWT token\n"
				+ "code:404");
	}

}