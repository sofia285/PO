package prr.terminals;

public class SilenceState extends TerminalState{

    public SilenceState(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void on() {
        getTerminal().setTerminalState(new IdleState(getTerminal()));
        getTerminal().setState("IDLE");

    }

    @Override
    public void off() {
        getTerminal().setTerminalState(new OffState(getTerminal()));
        getTerminal().setState("OFF");
    }

    @Override
    public void silence() {}

    @Override
    public void startCommunication() {
        getTerminal().setTerminalState(new BusyState(getTerminal()));
        getTerminal().setState("BUSY");
    }

    @Override
    public void endCommunication() {}

}
