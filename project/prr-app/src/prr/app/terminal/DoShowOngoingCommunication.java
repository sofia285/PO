package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

	DoShowOngoingCommunication(Network context, Terminal terminal) {
		super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
				if (_network.getOnCommunications(_receiver).size() == 0)
					_display.popup(Message.noOngoingCommunication());
				else {_display.popup(_network.getOnCommunications(_receiver));}
	}
}
