package prr.exceptions;

public class InvalidTerminalKeyException extends Exception {

    private static final long serialVersionUID = 202208091753L;

    private String _key;

    public InvalidTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
    return _key;
    }
}