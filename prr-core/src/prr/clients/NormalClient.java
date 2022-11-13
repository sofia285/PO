package prr.clients;

public class NormalClient extends ClientType{

    public NormalClient(Client client) {
        super(client);
    }

    public void changeType(){
        if (getClient().getBalance() > 500) {
            getClient().setType(new GoldClient(getClient()));
        }
    }

    @Override
    public String toString() {
        return "NORMAL";
    }
    
}
