package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.repository.ClientRepository;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ClientService implements ClientServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients: method entered");
        List<Client> clients = clientRepository.findAllWithRentsAndMovies();
        log.trace("getAllClients: result={}", clients);
        return clients;
    }

    @Override
    public Client saveClient(@NotNull @Valid Client client) {
        log.trace("saveClient - method entered: client={}", client);
        Client savedClient = this.clientRepository.save(client);
        log.trace("saveClient - method finished");
        return savedClient;
    }

    @Override
    @Transactional
    public Client updateClient(@NotNull @Valid Client client) {
        log.trace("updateClient - method entered: client={}", client);
        Client update = this.clientRepository.findByName(client.getName());
        this.clientRepository.updateClient(client);
        log.trace("updateClient - method finished");
        return update;
    }

    @Override
    public void deleteClient(@Min(0) Integer id) {
        log.trace("deleteClient - method entered: id={}", id);
        //List<Rent> rents = rentRepository.findAll().stream().filter(p -> p.getClientId()==id).collect(Collectors.toList());
        //rents.stream().forEach(p -> rentRepository.deleteById(p.getId()));
        clientRepository.deleteById(id);
        log.trace("deleteClient - method finished");
    }

    @Override
    public Client findByName(String name) {
        List<Client> clients = this.clientRepository.findAll();
        for(Client c: clients)
            if(c.getName().equals(name))
                return c;
        return null;
    }
}
