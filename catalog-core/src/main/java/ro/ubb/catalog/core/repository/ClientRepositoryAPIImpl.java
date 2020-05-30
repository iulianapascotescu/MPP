package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Client_;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.model.Rent_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class ClientRepositoryAPIImpl extends CustomRepositorySupport
        implements ClientRepositoryAPI {

    public List<Client> findAllWithRentsAndMovies() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        Fetch<Client, Rent> authorBookFetch = root.fetch(Client_.rents, JoinType.LEFT);
        authorBookFetch.fetch(Rent_.movie, JoinType.LEFT);
        List<Client> clients = entityManager.createQuery(query).getResultList();
        return clients;
    }


    public void updateClient(Client client) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Client> query = criteriaBuilder.createCriteriaUpdate(Client.class);
        Root<Client> root = query.from(Client.class);
        root.alias("p");
        query.set(root.get("name"), client.getName());
        query.set(root.get("age"), client.getAge());
        Predicate teamName = criteriaBuilder.equal(root.get("id"), client.getId());
        query.where(teamName);
        Query q = entityManager.createQuery(query);
        q.executeUpdate();
    }


}

