package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("terminalKey", Prompt.terminalKey());
                addStringField("Text", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        String key = stringField("terminalKey");
                        if (_network.getTerminal(key).canReciveText()) {
                                _network.registerCommunication("TEXT", _receiver.getTerminalId(), key, stringField("Text"));
                                _network.endTextCommunication(_receiver.getTerminalId());
                        }
                        else {_display.popup(Message.destinationIsOff(key));}

                
                } catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey()); }
        }
} 
