package ro.ubb.catalog.core.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;

import javax.persistence.Query;
import java.util.List;

public class ClientRepositorySQLImpl extends CustomRepositorySupport
        implements ClientRepositorySQL {

    @Transactional
    @Override
    public List<Client> findAllWithRentsAndMovies() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from client a " +
                "left join rent b on a.id=b.client_id " +
                "left join movie p on b.movie_id=p.id ")
                .addEntity("a",Client.class)
                .addJoin("b", "a.rents")
                .addJoin("p", "b.movie")
                .addEntity("a",Client.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> clients = query.getResultList();
        return clients;
    }

    public void updateClient(Client client) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("update client a set name='" + client.getName()+"', age="+client.getAge() + " where id="+client.getId())
                .addEntity("a",Client.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        query.executeUpdate();
    }

}


