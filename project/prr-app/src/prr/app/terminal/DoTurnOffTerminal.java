package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;
import prr.terminals.OffState;
import prr.terminals.IdleState;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (_receiver.isOff()) 
			_display.popup(Message.alreadyOff());
		else {_receiver.off();}
	}
}
