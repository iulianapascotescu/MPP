package ro.ubb.catalog.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.Client;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/repoClient.xml")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findByName(){
        Client client = this.clientRepository.findByName("client1");
        assertNotNull(client);
        assertEquals(client.getName(),"client1");
    }

    @Test
    public void findAllSimple(){
        List<Client> clients = this.clientRepository.findAll();
        assertEquals("there should be 2 clients", 2, clients.size());
    }

    @Test
    public void findAllWithRentsAndMovies(){
        List<Client> clients = this.clientRepository.findAllWithRentsAndMovies();
        assertEquals("there should be 2 clients", 2, clients.size());
    }

    @Test
    public void deleteClient(){
        List<Client> old_clients = clientRepository.findAll();
        assertEquals("there should be 2 clients", 2, old_clients.size());
        Client c = clientRepository.findByName("client1");
        assertNotNull(c);
        clientRepository.delete(c);
        List<Client> clients = clientRepository.findAll();
        assertEquals("there should be 1 client", 1, clients.size());
    }


}