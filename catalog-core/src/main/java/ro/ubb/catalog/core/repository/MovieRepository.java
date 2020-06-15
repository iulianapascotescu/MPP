package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieRepository extends InterfaceRepository<Movie, Integer>, MovieRepositoryAPI, MovieRepositoryJPQL, MovieRepositorySQL {
    List<Movie> findByYearIsGreaterThanEqualOrderByTitle(int year);

    //@Query("select distinct a from Movie a")
    //@EntityGraph(value = "movieWithRentsAndClients", type = EntityGraph.EntityGraphType.LOAD)
    //List<Movie> findAllWithRentsAndClients();

    @Query("select distinct a from Movie a")
    @EntityGraph(value = "movieWithRents", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAll();

    @Query("select distinct a from Movie a where a.title=:title")
    @EntityGraph(value = "movieWithRents", type = EntityGraph.EntityGraphType.LOAD)
    Movie findByTitle(@Param("title") String title);
}
