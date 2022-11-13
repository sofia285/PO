package prr.clients;

import java.io.Serializable;
import java.lang.Thread.State;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;

import prr.exceptions.DuplicateClientKeyException;
import prr.terminals.Terminal;
import prr.communications.Communication;


public class Client implements Serializable {

  /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

  
  private final String _id;
  
  private String _name;
  
  private int _taxId;
  
  private ClientType _type = new NormalClient(this);
  
  private String _notifications = "YES";
  
  private double _payments = 0;
  
  private double _debts = 0;

  private Integer _consecutiveVideoCommunications = 0;

  private Integer _consecutiveTextCommunications = 0;
  
  private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>();

  private Map<Integer, Communication> _sentCommunications = new TreeMap<Integer, Communication>();

  private Map<Integer, Communication> _receivedCommunications = new TreeMap<Integer, Communication>();


  public Client(String id, String name, int taxId) {
    _id = id;
    _name = name;
    _taxId = taxId;
  }

  public String getId() {
    return _id;
  }

  public String getName() {
    return _name;
  }

  public final void setName(String name) {
    _name = name;
  }

  public int getTaxId() {
    return _taxId;
  }

  public ClientType getType() {
    return _type;
  }

  public void setType(ClientType clientType) {
    _type = clientType;
  }

  public String getNotifications() {
    return _notifications;
  }

  public void setNotifications(String notifications) {
    _notifications = notifications;
  }

  public int getTerminals() {
    return _terminals.size();
  }

  public double getPayments() {
    return _payments;
  }

  public void increasePayments(double value) {
    _payments += value;
  }

  public double getDebts() {
    return _debts;
  }

  public void increaseDebts(double value) {
    _debts += value;
  }

  public void decreaseDebts(double value) {
    _debts -= value;
  }

  public double getBalance() {
    return _payments - _debts;
  }

  public void addTerminal(String terminalId, Terminal terminal) {

    _terminals.put(terminalId, terminal);
  }

  public Integer getConsecutiveVideoCommunications() {
    return _consecutiveVideoCommunications;
  }

  public void resetConsecutiveVideoCommunications() {
    _consecutiveVideoCommunications = 0;
  }

  public Integer getConsecutiveTextCommunications() {
    return _consecutiveTextCommunications;
  }

  public void resetConsecutiveTextCommunications() {
    _consecutiveTextCommunications = 0;
  }

  public Collection<Communication> getClientSentCommunications() {
    return Collections.unmodifiableCollection(_sentCommunications.values());
  }

  public void addSentCommunication(Integer communicationId, Communication communication) {
    if (communication.getType().equals("VIDEO")) {
      _consecutiveVideoCommunications += 1; 
    }

    else if (communication.getType().equals("TEXT")) {
      _consecutiveTextCommunications += 1;
    }
    _sentCommunications.put(communicationId, communication);
  }

  public Collection<Communication> getClientReceivedCommunications() {
    return Collections.unmodifiableCollection(_receivedCommunications.values());
  }

  public void addReceivedCommunication(Integer communicationId, Communication communication) {
    _receivedCommunications.put(communicationId, communication);
  }
  
  public String toString() {
    return "CLIENT|" + getId() + "|" + getName() + "|" + getTaxId() + "|" + getType()
    + "|" + getNotifications() + "|" + getTerminals() + "|" + (Long)Math.round(getPayments()) + "|"
    + (Long)Math.round(getDebts());
  }

} 
