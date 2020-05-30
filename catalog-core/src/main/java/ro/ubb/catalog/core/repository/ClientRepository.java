package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import java.util.List;

public interface ClientRepository extends InterfaceRepository<Client, Integer>,ClientRepositorySQL, ClientRepositoryAPI, ClientRepositoryJPQL{

    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithRents", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRents();

    //@Query("select distinct a from Client a")
    //@EntityGraph(value = "clientWithRentsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    //List<Client> findAllWithRentsAndMovies();

    @Query("select distinct a from Client a")
    @EntityGraph(value = "clients", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAll();

    @Query("select distinct a from Client a where a.name=:name")
    @EntityGraph(value = "clientWithRents", type = EntityGraph.EntityGraphType.LOAD)
    Client findByName(@Param("name") String name);

    //@Transactional
    @Modifying
    @Query(value = "insert into client(id, name, age) values (:id, :name, :age)", nativeQuery = true)
    @EntityGraph(value = "clientWithRentsAndMovies", type = EntityGraph.EntityGraphType.LOAD)
    void insertClient(@Param("id") int id, @Param("name") String name, @Param("age") int age);
}
