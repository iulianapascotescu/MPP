package ro.ubb.catalog.core.repository;

import lombok.Builder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.NotFound;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Rent;

import javax.enterprise.util.Nonbinding;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class ClientRepositoryImpl extends CustomRepositorySupport
        implements ClientRepositoryCustom {
    @Override
    public List<Client> findAllWithRentsAndMoviesJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct a from Client a " +
                        "left join fetch a.rents b " +
                        "left join fetch b.client");
        List<Client> clients = query.getResultList();
        return clients;
    }

    @Override
    public List<Client> findAllWithRentsAndMoviesCriteriaAPI() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
//        query.select(root);
        //Fetch<Client, Rent> authorBookFetch = root.fetch(Client_.rents, JoinType.LEFT);
        //authorBookFetch.fetch(Rent_.movie, JoinType.LEFT);

        List<Client> clients = entityManager.createQuery(query).getResultList();

        return clients;
    }

    @Override
    @Transactional
    public List<Client> findAllWithRentsAndMoviesSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from client a " +
                "left join rent b on a.id=b.client_id " +
                "left join movie p on b.movie_id=p.id ")
                .addEntity("a",Client.class)
                .addJoin("b", "a.rents")
                .addJoin("p", "b.client")
                .addEntity("a",Client.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> clients = query.getResultList();
        return clients;
    }
}
