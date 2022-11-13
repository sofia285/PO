package prr.app.terminal;

import javax.sound.midi.Receiver;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		addStringField("terminalKey", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_network.removeFriend(stringField("terminalKey"), _receiver);
		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}
