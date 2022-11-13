package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;
import prr.communications.Communication;
import prr.communications.InteractiveCommunication;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addIntegerField("units", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
				Communication communication = _network.getInteractiveCommunication(_receiver.getTerminalId());
				//communication.endInteractiveCommunication(integerField("units"));
				//_receiver.endCommunication(); 
				_network.priceInteractiveCommunication(_receiver.getTerminalId(), integerField("units"));
				_network.endInteractiveCommunication(_receiver.getTerminalId());
				_display.popup(Message.communicationCost((Long)Math.round(communication.getPrice())));
		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey()); 
		}
	}
}
