package prr.communications;

public class TextCommunication extends Communication {


    public TextCommunication(String type, Integer communicationId, String senderId, String receiverId, Integer units) {
        super(type, communicationId, senderId, receiverId, units);
    }


    @Override
    public String toString() {
        return getType() + "|" + getCommunicationId() + "|" + getSenderId() + "|" + 
            getReceiverId() + "|" + getUnits() + "|" + (Long)Math.round(getPrice()) + "|" + getStatus();
    }
    
}
