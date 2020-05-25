package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientRepository extends InterfaceRepository<Client, Integer>, ClientRepositoryCustom{
    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithRents", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRents();

    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithRentsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentsAndMovies();

    @Query("select distinct a from Client a where a.name=:name")
    @EntityGraph(value = "clientWithRents", type = EntityGraph.EntityGraphType.LOAD)
    Client findByName(String name);
}
