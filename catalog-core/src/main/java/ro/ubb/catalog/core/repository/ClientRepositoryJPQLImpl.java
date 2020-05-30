package ro.ubb.catalog.core.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ClientRepositoryJPQLImpl extends CustomRepositorySupport
        implements ClientRepositoryJPQL {

    public List<Client> findAllWithRentsAndMovies(){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct a from Client a " +
                        "left join fetch a.rents b " +
                        "left join fetch b.movie");
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


    /*
    @Override
    @Transactional
    public Client addClientJPQL(Client client) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        String sqlString= "INSERT INTO client (id,name, age) VALUES (" +client.getId()+", '"+ client.getName() +"', "+ client.getAge()+")";
        Query query = entityManager.createNativeQuery(sqlString);
        int result = query.executeUpdate();
        return client;
    }
     */
}
