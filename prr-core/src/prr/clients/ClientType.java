package prr.clients;

import java.io.Serializable;

public abstract class ClientType implements Serializable {
    private Client _client;

    public ClientType(Client client) {
        _client = client;
    }

    abstract public void changeType();

    public Client getClient() {
        return _client;
    }
}
