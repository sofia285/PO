package prr.communications;

import java.io.Serializable;

import prr.clients.Client;
import prr.clients.ClientType;
import prr.terminals.Terminal;

abstract public class Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;

    private String _type;

    private int _communicationId;

    private String _senderId;

    private String _receiverId;

    private Integer _units;

    private double _price = 0;

    private String _status = "FINISHED";

    public Communication(){}

    public Communication(String type, Integer communicationId, String senderId, String receiverId, Integer units) {
        _type = type;
        _communicationId = communicationId;
        _senderId = senderId;
        _receiverId = receiverId;
        _units = units;
    }

    public void endTextCommunication(String type) {
        textPrice(type);
    }

    public void endInteractiveCommunication(String type, Integer units) {
        setUnits(units);
        interactivePrice(type);
    }

    public String getType() {
        return _type;
    }

    public Integer getCommunicationId() {
        return _communicationId;
    }

    public String getSenderId() {
        return _senderId;
    }

    public String getReceiverId() {
        return _receiverId;
    }

    public Integer getUnits() {
        return _units;
    }

    public void setUnits(Integer units) {
        _units = units;
    }

    public double getPrice() {
       return _price;
    }

    public void setPrice(double price) {
        _price = price;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public void applyDiscount() {
        _price = _price * 0.5;
    }

    public void textPrice(String type) {
        if (type.equals("NORMAL")) {
            if (getUnits() < 50) {
                setPrice(10);
            }
    
            else if (getUnits() >= 50 && getUnits() < 100) {
                setPrice(16);
            }
    
            else if (getUnits() >= 100) {
                setPrice(2 * getUnits());
            }
        }

        else if (type.equals("GOLD")) {
            if (getUnits() < 50) {
                setPrice(10);
            }

            else if (getUnits() >= 50 && getUnits() < 100) {
                setPrice(10);
            }

            else if (getUnits() >= 100) {
                setPrice(2 * getUnits());
            }
        }

        else if (type.equals("PLATINUM")) {
            if (getUnits() < 50) {
                setPrice(0);
            }
    
            else if (getUnits() >= 50 && getUnits() < 100) {
                setPrice(4);
            }
    
            else if (getUnits() >= 100) {
                setPrice(4);
            }
        }
        
    }

    public void interactivePrice(String type) {
        if (type.equals("NORMAL")) {
            if (getType().equals("VOICE")){
                setPrice(20 * getUnits());
            }
            else if (getType().equals("VIDEO")){
                setPrice(30 * getUnits());
            }
        }

        else if (type.equals("GOLD")) {
            if (getType().equals("VOICE")){
                setPrice(10 * getUnits());
            }
            else if (getType().equals("VIDEO")){
                setPrice(20 * getUnits());
            }
        }

        else if (type.equals("PLATINUM")) {
            if (getType().equals("VOICE")){
                setPrice(10 * getUnits());
            }
            else if (getType().equals("VIDEO")){
                setPrice(10 * getUnits());
            }
        }
    }
    
}
