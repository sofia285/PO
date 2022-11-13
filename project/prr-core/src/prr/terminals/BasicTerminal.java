package prr.terminals;

public class BasicTerminal extends Terminal {

    private String _type = "BASIC";

    public BasicTerminal(String terminalId, String clientId, String state) {
        super(terminalId, clientId, state);
    }

    public String getType() {
        return _type;
    }

    public String toString() {
        return getType() + "|" + getTerminalId() + "|" + getClientId() + "|" + getState() + "|" +
        (Long)Math.round(getBalancePayments()) + "|" + (Long)Math.round(getBalanceDebts()) + getFriends();
    }
}
