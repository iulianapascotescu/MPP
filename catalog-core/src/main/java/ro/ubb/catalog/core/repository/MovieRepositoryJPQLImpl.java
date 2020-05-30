package ro.ubb.catalog.core.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MovieRepositoryJPQLImpl extends CustomRepositorySupport implements MovieRepositoryJPQL {
    @Override
    @Transactional
    public void updateMovie(Movie movie) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        String srt = "update Movie a set a.title='" + movie.getTitle() + "', a.genre='" + movie.getGenre() + "', a.year=" + movie.getYear() + " where a.id=" + movie.getId();
        Query query = entityManager.createQuery(srt);
        query.executeUpdate();
    }

    @Override
    public List<Movie> findAllWithRentsAndClients() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct a from Movie a " +
                        "left join fetch a.rents b " +
                        "left join fetch b.client");
        List<Movie> movies = query.getResultList();
        return movies;
    }

}
