package service.client;

import model.Account;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientManagementService {

    public Notification<Client> deleteClient(Client client);
    public Notification<Client> addClient(Client client);
    public Notification<Client> findClient(Long id);
    public Notification<Boolean> updateClient(Client client1, Client client2);
    public Notification<Boolean> addAccount(Client client, Account account);
    public List<Client> findAllClients();
    public Notification<List<Account>> findAllAccountsForClient(Client client);
    public void setUserId(Long id);


}
