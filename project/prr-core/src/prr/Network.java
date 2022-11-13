package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import prr.clients.Client;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.ImportFileException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.terminals.BasicTerminal;
import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.InteractiveCommunication;


/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private Map<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);

  private Map<String, Terminal> _terminals = new TreeMap<String, Terminal>(String.CASE_INSENSITIVE_ORDER);

  private Map<Integer, Communication> _communications = new TreeMap<Integer, Communication>();


  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException                if there is an IO erro while processing
   *                                    the text file
   * @throws ImportFileException
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException {

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] fields = line.split("\\|");
        try {
          registerEntry(fields);
        } catch (DuplicateClientKeyException | DuplicateTerminalKeyException | 
          UnknownClientKeyException | InvalidTerminalKeyException | UnknownTerminalKeyException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e1) {
      throw new ImportFileException(filename);
    }
  }
  
  /**
   * @param key
   * @return 
   * @throws UnknownClientKeyException
   */
  public Client getClient(String key) throws UnknownClientKeyException {
    if (!(_clients.containsKey(key)))
      throw new UnknownClientKeyException(key);
    return _clients.get(key);
  }

  /**
   * @return all clients as an unmodifiable collection.
   */
  public Collection<Client> getAllClients() {
    return Collections.unmodifiableCollection(_clients.values());
  }

  public Collection<Client> getClientsWithDebts(){
    List<Client> clients = new ArrayList<Client>();
    for(Client client : _clients.values()){
      if(client.getDebts() > 0){
        clients.add(client) ;
      }
    }
    return clients ;
  }  

  /**
   * @return all clients without debts
   */
  public Collection<Client> getClientsWithoutDebts(){
    List<Client> clients = new ArrayList<Client>();
    for(Client client : _clients.values()){
      if(client.getDebts() == 0){
        clients.add(client) ;
      }
    }
    return clients ;
  }

  /**
   * @param id
   * @param client
   */
  public void addClient(String id, Client client) {
    _clients.put(id, client);
  }

  /**
   * @param key
   * @return
   */
  public Terminal getTerminal(String key) throws UnknownTerminalKeyException{
    if (!(_terminals.containsKey(key)))
      throw new UnknownTerminalKeyException(key);
    return _terminals.get(key);
    
  }

  /**
   * @return all terminals
   */
  public Collection<Terminal> getAllTerminals() {
    return Collections.unmodifiableCollection(_terminals.values());
  }

  /**
   * @param fields
   * @throws DuplicateTerminalKeyException
   * @throws DuplicateClientKeyException
   * @throws UnrecognizedEntryException
   * @throws UnknownClientKeyException
   * @throws InvalidTerminalKeyException
   * @throws UnknownTerminalKeyException
   */
  public void registerEntry(String[] fields) throws DuplicateTerminalKeyException, DuplicateClientKeyException, 
    UnrecognizedEntryException, UnknownClientKeyException, InvalidTerminalKeyException, UnknownTerminalKeyException {

    switch (fields[0]) {
      case "CLIENT" -> importRegisterClient(fields);
      case "BASIC", "FANCY" -> registerTerminal(fields);
      case "FRIENDS" -> registerFriend(fields);
      default -> throw new UnrecognizedEntryException(fields[0]);
    }
  }

  /**
   * @param fields
   * @throws DuplicateClientKeyException
   */
  public void importRegisterClient(String[] fields) throws DuplicateClientKeyException {

    String key = fields[1];
    if (_clients.containsKey(key)) {
      throw new DuplicateClientKeyException(key);
    }

    registerClient(key, fields[2], Integer.parseInt(fields[3]));
  }

  /**
   * @param key
   * @param name
   * @param taxId
   * @throws DuplicateClientKeyException
   */
  public void registerClient(String key, String name, int taxId) throws DuplicateClientKeyException{
    if (_clients.containsKey(key)) {
     throw new DuplicateClientKeyException(key);
    }

    Client c = new Client(key, name, taxId);
    _clients.put(key, c);
  }

  /**
   * @param clientId
   * @param notifications
   * @throws UnknownClientKeyException
   */
  public void setClientNotifications(String clientId, String notifications) throws UnknownClientKeyException {
    if (!(_clients.containsKey(clientId)))
      throw new UnknownClientKeyException(clientId);
    _clients.get(clientId).setNotifications(notifications);
  }

  /**
   * @param s
   * @return
   */
  public static boolean isInt(String s) {
    try {
      Integer.parseInt(s); 
      return true;
    } catch(NumberFormatException e) {
      return false;
    }
  }
  
  /**
   * @param fields
   * @throws DuplicateTerminalKeyException
   * @throws UnknownClientKeyException
   * @throws InvalidTerminalKeyException
   */
  public void registerTerminal(String[] fields) throws DuplicateTerminalKeyException, UnknownClientKeyException, InvalidTerminalKeyException {
    
    if ((fields[1].length() != 6) || (!(isInt(fields[1])))) {
      throw new InvalidTerminalKeyException(fields[1]);
    }

    if (_terminals.get(fields[1]) != null) {
      throw new DuplicateTerminalKeyException(fields[1]);
    }

    if (_clients.get(fields[2]) == null) {
      throw new UnknownClientKeyException(fields[2]);
    }

    Terminal terminal = switch (fields[0]) {
      case "BASIC" -> new BasicTerminal(fields[1], fields[2], fields[3]);
      case "FANCY" -> new FancyTerminal(fields[1], fields[2], fields[3]);
      default -> new BasicTerminal(fields[1], fields[2], fields[3]);
    };

    _clients.get(fields[2]).addTerminal(fields[1], terminal);
    _terminals.put(fields[1], terminal);
  }

  /**
   * @param fields
   * @throws UnknownTerminalKeyException
   */
  public void registerFriend(String[] fields) throws UnknownTerminalKeyException{
    String[] friend = fields[2].split(",");
    for (String i : friend) {
      if (!(_terminals.containsKey(i)))
        throw new UnknownTerminalKeyException(i);
      _terminals.get(fields[1]).addFriend(i);
    }
  }

  public void addFriend(String terminalId, Terminal terminal) throws UnknownTerminalKeyException{
    if (!(_terminals.containsKey(terminalId)))
        throw new UnknownTerminalKeyException(terminalId);
    else {terminal.addFriend(terminalId);}
  }

  public void removeFriend(String terminalId, Terminal terminal) throws UnknownTerminalKeyException{
    if (!(_terminals.containsKey(terminalId)))
        throw new UnknownTerminalKeyException(terminalId);
    else {terminal.removeFriend(terminalId);}
  }

  /**
   * @return unused terminals
   */
  public Collection<Terminal> getUnusedTerminals() {
    List<Terminal> terminals = new ArrayList<Terminal>();
    for(Terminal terminal : _terminals.values()){
      if(terminal.nCommunications() == 0){
        terminals.add(terminal);
      }
    }
    return terminals ;
  }

  /**
   * @return all terminals with positive balance
   */
  public Collection<Terminal> getTerminalsWithPositiveBalance() {
    List<Terminal> terminals = new ArrayList<Terminal>();
    for(Terminal terminal : _terminals.values()){
      if(terminal.getBalancePayments() > terminal.getBalanceDebts()){
        terminals.add(terminal);
      }
    }
    return terminals;
  }

  /**
   * @return number of communications
   */
  public Integer countCommunications() {
    return _communications.size();
  }

  /**
   * @param type
   * @param senderId
   * @param receiverId
   * @param text
   */
  public void registerCommunication(String type, String senderId, String receiverId, String text) {
    Integer units = text.length();
    Integer communicationId =  countCommunications() + 1;
    Communication communication = switch (type){
      case "TEXT" ->  new TextCommunication(type, communicationId, senderId, receiverId, units);
      case "VOICE" ->  new InteractiveCommunication(type, communicationId, senderId, receiverId, units);
      case "VIDEO" ->  new InteractiveCommunication(type, communicationId, senderId, receiverId, units);
      default -> new TextCommunication(type, communicationId, senderId, receiverId, units);
    };
    
    _terminals.get(senderId).addCommunication(communicationId, communication);
    _communications.put(communicationId, communication);
    _clients.get(_terminals.get(senderId).getClientId()).addSentCommunication(communicationId, communication);
    _clients.get(_terminals.get(receiverId).getClientId()).addReceivedCommunication(communicationId, communication);
  }

  /**
   * @param terminal
   * @param communicationId
   */
  public void performPayment(Terminal terminal, Integer communicationId) {
    Communication communication = terminal.getTerminalCommunication(communicationId);
    double price = communication.getPrice();
    terminal.increasePayments(price);
    terminal.decreaseDebts(price);
    _clients.get(terminal.getClientId()).increasePayments(price);
    _clients.get(terminal.getClientId()).decreaseDebts(price);
    _clients.get(terminal.getClientId()).getType().changeType();
  }

  public void priceInteractiveCommunication(String terminalId, Integer units) throws UnknownTerminalKeyException {
    String clientId = _terminals.get(terminalId).getClientId();
    for (Communication communication : _communications.values()) {
      if (communication.getSenderId().equals(terminalId) && communication.getStatus().equals("ONGOING")) {
        communication.endInteractiveCommunication(_clients.get(clientId).getType().toString(), units);
      }
    }
  }

  /**
   * @param terminalId
   * @throws UnknownTerminalKeyException
   */
  public void endInteractiveCommunication(String terminalId) throws UnknownTerminalKeyException {
    String clientId = _terminals.get(terminalId).getClientId();
    for (Communication communication : _communications.values()) {
      if (communication.getSenderId().equals(terminalId) && communication.getStatus().equals("ONGOING")) {
        if (_terminals.get(terminalId).containsFriend(communication.getReceiverId())) {
          communication.applyDiscount();
        }
        _clients.get(clientId).increaseDebts(communication.getPrice()); 
        _terminals.get(terminalId).increaseDebts(communication.getPrice());
        String SenderId = communication.getSenderId();
        String receiverId = communication.getReceiverId();
        getTerminal(SenderId).endCommunication();
        getTerminal(receiverId).endCommunication();
        communication.setStatus("FINISHED");
      }
    }
    _clients.get(clientId).getType().changeType();
  }

  /**
   * @param terminalId
   */
  public void endTextCommunication(String terminalId) {
    String clientId = _terminals.get(terminalId).getClientId();
    for (Communication communication : _communications.values()) {
      if (communication.getSenderId().equals(terminalId) && communication.getCommunicationId() == countCommunications()) {
        communication.endTextCommunication(_clients.get(clientId).getType().toString());
        if (_terminals.get(terminalId).containsFriend(communication.getReceiverId())) {
          communication.applyDiscount();
        }
        _clients.get(_terminals.get(terminalId).getClientId()).increaseDebts(communication.getPrice()); 
        _terminals.get(terminalId).increaseDebts(communication.getPrice());
      }
    }
    _clients.get(_terminals.get(terminalId).getClientId()).getType().changeType();
  }

  /**
   * @param communicationId
   * @return
   */
  public Communication getCommunication(Integer communicationId) {
    return _communications.get(communicationId);
  }

  /**
   * @return all communications
   */
  public Collection<Communication> getAllCommunications() {
    return Collections.unmodifiableCollection(_communications.values());
  }



  public Collection<Communication> getOnCommunications(Terminal terminal) {
    List<Communication> communications = new ArrayList<Communication>();
    for(Communication communication : _communications.values()){
      if(communication.getStatus().equals("ONGOING") && terminal.getTerminalId().equals(communication.getSenderId())){
        communications.add(communication);
      }
    }
    return communications ;
  }

  /**
   * @param terminalId
   * @return interactive communications
   */
  public Communication getInteractiveCommunication(String terminalId) {
    Communication communications = new InteractiveCommunication();
    for(Communication communication : _communications.values()) {
      if(communication.getSenderId().equals(terminalId)){
        communications = communication;
      }
    }
    return communications;
  }

  /**
   * @param clientId
   * @return sent communications 
   */
  public Collection<Communication> getCommunicationsFromClient(String clientId) {
    return _clients.get(clientId).getClientSentCommunications();
  }

  /**
   * @param clientId
   * @return recived communications
   */
  public Collection<Communication> getCommunicationsToClient(String clientId) {
    return _clients.get(clientId).getClientReceivedCommunications();
  }

  /**
   * @return payments
   */
  public Long getGlobalPayments() {
    double payments = 0;
    for (Client client : _clients.values()) {
      payments += client.getPayments();
    }
    return (Long)Math.round(payments);
  }

  /**
   * @return debts
   */
  public Long getGlobalDebts() {
    double debts = 0;
    for (Client client : _clients.values()) {
      debts += client.getDebts();
    }
    return (Long)Math.round(debts);
  }


}
