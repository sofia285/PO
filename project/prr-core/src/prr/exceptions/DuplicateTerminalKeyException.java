package prr.exceptions;

public class DuplicateTerminalKeyException extends Exception{

    private static final long serialVersionUID = 202208091753L;

    private String _key;

    public DuplicateTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
    return _key;
    }
}
