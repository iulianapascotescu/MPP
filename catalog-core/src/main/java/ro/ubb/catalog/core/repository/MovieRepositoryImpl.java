package ro.ubb.catalog.core.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class MovieRepositoryImpl extends CustomRepositorySupport
        implements MovieRepositoryCustom {

    @Override
    public List<Movie> findAllWithRentsAndClientsJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct a from Movie a " +
                        "left join fetch a.rents b " +
                        "left join fetch b.client");
        List<Movie> movies = query.getResultList();
        return movies;
    }

    @Override
    public List<Movie> findAllWithRentsAndClientsCriteriaAPI() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        query.distinct(Boolean.TRUE);
        Root<Movie> root = query.from(Movie.class);
        Fetch<Movie, Rent> movieRentFetch = root.fetch(Movie_.rents, JoinType.LEFT);
        movieRentFetch.fetch(Rent_.client, JoinType.LEFT);
        List<Movie> movies = entityManager.createQuery(query).getResultList();
        return movies;
    }

    @Override
    @Transactional
    public List<Movie> findAllWithRentsAndClientsSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from movie a " +
                "left join rent b on a.id=b.movie_id " +
                "left join client p on b.client_id=p.id ")
                .addEntity("a",Movie.class)
                .addJoin("b", "a.rents")
                .addJoin("p", "b.client")
                .addEntity("a",Movie.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Movie> movies = query.getResultList();
        return movies;
    }

    @Override
    public void updateMovieJPQL(Movie movie) {
        EntityManager entityManager = getEntityManager().getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String srt = "update Movie a set a.title='"+movie.getTitle()+"', a.genre='"+movie.getGenre()+"', a.year="+movie.getYear()+" where a.id="+movie.getId();
        Query query = entityManager.createQuery(srt);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    @Transactional
    public Movie addMovieJPQL(Movie movie) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        String sqlString= "INSERT INTO Movie (id,title, genre, year) VALUES (" +movie.getId()+", '"+ movie.getTitle() +"', '"+ movie.getGenre()+"',"+ movie.getYear()+")";
        Query query = entityManager.createNativeQuery(sqlString);
        int result = query.executeUpdate();
        return movie;
    }

    @Override
    public void updateMovieCriteriaAPI(Movie movie) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Movie> query = criteriaBuilder.createCriteriaUpdate(Movie.class);
        Root<Movie> root = query.from(Movie.class);
        root.alias("p");
        query.set(root.get("title"), movie.getTitle());
        query.set(root.get("genre"), movie.getGenre());
        query.set(root.get("year"), movie.getYear());
        Predicate teamName = criteriaBuilder.equal(root.get("id"), movie.getId());
        query.where(teamName);
        Query q = entityManager.createQuery(query);
        q.executeUpdate();
    }

    @Override
    public void updateMovieSQL(Movie movie){
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("update movie a set title='" + movie.getTitle()+"', genre='"+movie.getGenre() + "', year="+ movie.getYear()+" where id="+movie.getId())
                .addEntity("a",Movie.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        query.executeUpdate();
    }


}
