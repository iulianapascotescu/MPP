package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientRepositorySQL {
    List<Client> findAllWithRentsAndMovies();

    void updateClient(Client client);
}
