package prr.terminals;

public class OffState extends TerminalState {
    public OffState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void on() {
        getTerminal().setTerminalState(new IdleState(getTerminal()));
        getTerminal().setState("IDLE");
    }

    @Override
    public void off() {}

    @Override
    public void silence() {}

    @Override
    public void startCommunication() {}
    
    @Override
    public void endCommunication() {}
    
}
