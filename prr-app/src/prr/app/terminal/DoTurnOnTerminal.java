package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;
import prr.terminals.OffState;
import prr.terminals.IdleState;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

	DoTurnOnTerminal(Network context, Terminal terminal) {
		super(Label.POWER_ON, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {			
		if (_receiver.isOn()) 
			_display.popup(Message.alreadyOn());
		else {_receiver.on();}
	}
}
