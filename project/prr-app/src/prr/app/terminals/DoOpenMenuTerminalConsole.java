package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
        try {    
			Form request = new Form();
			request.addStringField("terminalKey", Prompt.terminalKey());
			request.parse();
			(new prr.app.terminal.Menu(_receiver, _receiver.getTerminal(request.stringField("terminalKey")))).open();
		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}
