package repository.client;

import model.Client;


import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
    Client findById(Long id);

    boolean addClient(Client client);

    void deleteClient(Long id);

    void updateClient(Client oldClient, Client newClient);

    void deleteAll();
}
