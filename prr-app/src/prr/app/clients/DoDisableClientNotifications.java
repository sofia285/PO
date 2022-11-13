package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("key", Prompt.key());	}

	@Override
	protected final void execute() throws CommandException {
		try {
            if (_receiver.getClient(stringField("key")).getNotifications().equals("NO"))
                _display.popup(Message.clientNotificationsAlreadyDisabled());
            _receiver.setClientNotifications(stringField("key"), "NO");
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
	}
}
