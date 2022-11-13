package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("commKey", Prompt.commKey());

	}

	@Override
	protected final void execute() throws CommandException {
		_network.performPayment(_receiver, integerField("commKey"));
	}
}
