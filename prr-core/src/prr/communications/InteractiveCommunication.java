package prr.communications;

public class InteractiveCommunication extends Communication{

    public InteractiveCommunication(){
        super();
    }

    public InteractiveCommunication(String type, Integer communicationId, String senderId, String receiverId, Integer units) {
        super(type, communicationId, senderId, receiverId, units);
        super.setStatus("ONGOING");
    }

    @Override
    public String toString() {
        return getType() + "|" + getCommunicationId() + "|" + getSenderId() + "|" + 
            getReceiverId() + "|" + getUnits() + "|" + (Long)Math.round(getPrice()) + "|" + getStatus();
    }
    
}
