package prr.terminals;

public class IdleState extends TerminalState {

    public IdleState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void on() {}

    @Override
    public void off() {
        getTerminal().setTerminalState(new OffState(getTerminal()));
        getTerminal().setState("OFF");
    }

    @Override
    public void silence() {
        getTerminal().setTerminalState(new SilenceState(getTerminal()));
        getTerminal().setState("SILENCE");
    }

    @Override
    public void startCommunication() {
        getTerminal().setTerminalState(new BusyState(getTerminal()));
        getTerminal().setState("BUSY");
    }

    @Override
    public void endCommunication() {}

}
