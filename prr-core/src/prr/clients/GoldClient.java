package prr.clients;

public class GoldClient extends ClientType{
    public GoldClient(Client client) {
        super(client);
    }

    public void changeType(){
        if (getClient().getBalance() < 0){
            getClient().setType(new NormalClient(getClient()));
        }
        else if (getClient().getConsecutiveVideoCommunications() == 5 && getClient().getBalance() > 0) {
            getClient().setType(new PlatinumClient(getClient()));
            getClient().resetConsecutiveVideoCommunications();
        }
    }
}
