package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		//FIXME add command fields
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(Message.clientPaymentsAndDebts(_receiver.getClient(stringField("key")).getId(), 
				(Long)Math.round(_receiver.getClient(stringField("key")).getPayments()),
				(Long)Math.round(_receiver.getClient(stringField("key")).getDebts())));
		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownClientKeyException(e.getKey());
		}
	}
}
