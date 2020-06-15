package ro.ubb.catalog.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data.xml")
public class ClientServiceTest {

    @Autowired
    private ClientServiceInterface clientService;

    @Test
    public void findAll() throws Exception {
        List<Client> clients = clientService.getAllClients();
        assertEquals("there should be three clients", 3, clients.size());
    }

    @Ignore
    @Test
    public void addClient() throws Exception {
        Client client = new Client("name4", 33, null);
        client.setId(10);
        clientService.saveClient(client);
        List<Client> clients = clientService.getAllClients();
        assertEquals("there should be four clients", 4, clients.size());
    }

    @Test
    public void getClientByName() {
        Client c = clientService.findByName("client1");
        assertNotNull(c);
        assertEquals("client1", c.getName());
    }


    @Test
    public void updateClient() {
        Client c = new Client("client3", 20, null);
        c.setId(3);
        clientService.updateClient(c);
        Client new_client = clientService.findByName("client3");
        assertNotNull(new_client);
        assertEquals(c.getAge(), new_client.getAge());
        assertEquals(c.getName(), new_client.getName());
        List<Client> clients = clientService.getAllClients();
        assertEquals("there should be four students", 3, clients.size());
    }

    @Test
    public void deleteClient() {
        List<Client> clients = clientService.getAllClients();
        assertEquals("there should be 2 students", 3, clients.size());

        clientService.deleteClient(1);

        List<Client> clients1 = clientService.getAllClients();
        assertEquals("there should be 1 student", 2, clients1.size());
    }
}