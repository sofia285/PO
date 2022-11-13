package prr.clients;

public class PlatinumClient extends ClientType{
    public PlatinumClient(Client client) {
        super(client);
    }

    public void changeType(){
        if (getClient().getBalance() < 0) {
            getClient().setType(new NormalClient(getClient()));
        }

        else if (getClient().getConsecutiveTextCommunications() == 2 && getClient().getBalance() > 0) {
            getClient().setType(new GoldClient(getClient()));
            getClient().resetConsecutiveTextCommunications();
        }
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
