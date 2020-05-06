package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientServiceInterface {
    List<Client> getAllClients();

    Client saveClient(Client client);

    Client updateClient(Client client);

    void deleteClient(Integer id);
}
