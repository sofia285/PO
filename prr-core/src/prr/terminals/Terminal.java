package prr.terminals;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import prr.communications.Communication;
import prr.exceptions.UnknownTerminalKeyException;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        private String _terminalId;

        private String _clientId;

        private String _state;

        private TerminalState _terminalState = new IdleState(this);

        private double _balancePayments;

        private double _balanceDebts;

        private double _balance;

        private Map<Integer, Communication> _communications = new TreeMap<Integer, Communication>();

        private List<String> _friends = new ArrayList<String>();


        public Terminal(String terminalId, String clientId, String state) {
                _terminalId = terminalId;
                _clientId = clientId;
                if (state.equals("ON")) {_state = "IDLE";}
                else {_state = state;}
                
        }


        public String getTerminalId() {
                return _terminalId;
        }

        public String getClientId() {
                return _clientId;
        }

        
        public String getState() {
                return _state;
        }

        public void setState(String state) { _state = state;}

        
        public String getTerminalState() {
                return _terminalState.toString();
        } 

        public double getBalanceDebts() {
                return _balanceDebts;
        }

        public void increaseDebts(double value) {
                _balanceDebts += value;
        }

        public void decreaseDebts(double value) {
                _balanceDebts -= value;
        }

        public double getBalancePayments() {
                return _balancePayments;
        }

        public void increasePayments(double value) {
                _balancePayments += value;
        }

        public double getBalance(){
                _balance = _balancePayments - _balanceDebts;
                return _balance;
        }
        
        public Communication getTerminalCommunication(Integer key) {
                return _communications.get(key);
        }

        public Collection<Communication> getTerminalCommunications() {
                return Collections.unmodifiableCollection(_communications.values());
        }

        public void addCommunication(Integer communicationId, Communication communication) {
                _communications.put(communicationId, communication);
        }

        public Integer nCommunications() {
                return _communications.size();
        }

        public boolean containsFriend(String terminalId) {
                return _friends.contains(terminalId);
        }

        public String getFriends(){
                Collections.sort(_friends);
                if (_friends.size() != 0) {
                        String friends = "|";
                        int i = _friends.size();
                        for (String f : _friends) {
                                if (i > 1) {
                                        friends += f + ",";
                                }
                                else {
                                        friends += f;
                                }
                                i--;
                        }
                        return friends;  
                }  
                return "";
        }

        public void addFriend(String key) throws UnknownTerminalKeyException{
                if (!(getTerminalId().equals(key)) && !(_friends.contains(key)))
                        _friends.add(key); 
        }

        public void removeFriend(String key) throws UnknownTerminalKeyException{
                if (_friends.contains(key))
                        _friends.remove(key);
        }
        

        public void setTerminalState(TerminalState state) {
		_terminalState = state;
        }
        
        public void on() { _terminalState.on(); }

        public void off() { _terminalState.off(); }

        public void silence() { _terminalState.silence(); }

        public void startCommunication() {_terminalState.startCommunication(); }

        public void endCommunication() {_terminalState.endCommunication(); }

        public boolean isOff() {
                return getState().equals("OFF");
        }
        
        public boolean isOn() {
                return getState().equals("IDLE");
        }
        
        public boolean isSilent() {
                return getState().equals("SILENCE");
        }

        public boolean isBusy() {
                return getState().equals("BUSY");
        }


        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                if (getState().equals("BUSY")) { 
                        return true;
                }                
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                if (getState().equals("OFF") || getState().equals("BUSY"))
                        return false;
                return true;
        } 

        public boolean canReciveInteractiveCommunication() {
                if (getState().equals("OFF") || getState().equals("BUSY") || getState().equals("SILENCE"))
                        return false;
                return true;

        } 

        public boolean canReciveText() {
                if (getState().equals("OFF"))
                        return false;
                return true;
        }
       
}

