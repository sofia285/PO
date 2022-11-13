package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import prr.terminals.TerminalState;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("terminalKey", Prompt.terminalKey());
		addOptionField("commType", Prompt.commType(), "VOICE", "VIDEO");
	}

	@Override
	protected final void execute() throws CommandException {
                //FIXME implement command
		try {	
			String key = stringField("terminalKey");
			if (_network.getTerminal(key).canReciveInteractiveCommunication()) {
				_network.registerCommunication(stringField("commType"), _receiver.getTerminalId(), key,"");
				_receiver.startCommunication();
				_network.getTerminal(key).startCommunication();
			}

			else {
				if (_network.getTerminal(key).isBusy()) 
					_display.popup(Message.destinationIsBusy(key));
				if (_network.getTerminal(key).isOff()) 
					_display.popup(Message.destinationIsOff(key));
				if (_network.getTerminal(key).isSilent())
					_display.popup(Message.destinationIsSilent(key));
			}
			

		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey()); 
		} 		

	}
}
