package ro.ubb.catalog.core.repository;

import org.springframework.context.annotation.Primary;
import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientRepositoryAPI {

    List<Client> findAllWithRentsAndMovies();

    void updateClient(Client client);
}
