package service;

import domain.Client;
import domain.validators.MovieProjectException;

import java.util.List;

public interface ServiceClientInterface {

    void addClient(Client client) throws MovieProjectException;

    void deleteClient(Integer id) throws MovieProjectException;

    void updateClient(Client client) throws MovieProjectException;

    List<Client> getAllClients();

}
