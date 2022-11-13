package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (_receiver.isSilent()) 
			_display.popup(Message.alreadySilent());
		else {_receiver.silence();}
	}
}
