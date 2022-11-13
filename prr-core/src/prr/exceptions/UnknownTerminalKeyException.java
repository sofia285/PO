package prr.exceptions;

public class UnknownTerminalKeyException extends Exception{
    
    private static final long serialVersionUID = 202208091753L;

    private String _key;

    /** @param key */
	public UnknownTerminalKeyException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}

}
