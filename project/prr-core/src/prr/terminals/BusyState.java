package prr.terminals;

public class BusyState extends TerminalState{

    public BusyState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void on() {}

    @Override
    public void off() {}

    @Override
    public void silence() {}

    @Override
    public void startCommunication() {}

    @Override
    public void endCommunication() {
        getTerminal().setTerminalState(new IdleState(getTerminal()));
        getTerminal().setState("IDLE");
    }

}
