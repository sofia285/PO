package prr.exceptions;


public class UnknownClientKeyException extends Exception {
    
    private static final long serialVersionUID = 202208091753L;

    private String _key;

    /** @param key */
	public UnknownClientKeyException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
}
