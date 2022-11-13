package prr.terminals;

import java.io.Serializable;

public abstract class TerminalState implements Serializable{
    
    private Terminal _terminal;

    public TerminalState(Terminal terminal) {
        _terminal = terminal;
    }

    public Terminal getTerminal() {
        return _terminal;
    }

    public abstract void on();
    public abstract void off();
    public abstract void silence();
    public abstract void startCommunication();
    public abstract void endCommunication();

}
