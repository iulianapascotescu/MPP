package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;
import ro.ubb.catalog.core.repository.RentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientServiceInterface{
    public static final Logger log= LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RentRepository rentRepository;


    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients: method entered");
        List<Client> clients = clientRepository.findAll();
        log.trace("getAllClients: result={}", clients);
        return clients;
    }

    @Override
    public Client saveClient(Client client) {
        log.trace("saveClient - method entered: client={}", client);
        Client savedClient = this.clientRepository.save(client);
        log.trace("saveClient - method finished");
        return savedClient;
    }

    @Override
    public Client updateClient(Client client) {
        log.trace("updateClient - method entered: client={}", client);
        Client update = this.clientRepository.findById(client.getId()).orElse(client);
        this.clientRepository.save(client);
        log.trace("updateClient - method finished");
        return update;
    }

    @Override
    public void deleteClient(Integer id) {
        log.trace("deleteClient - method entered: id={}", id);
        List<Rent> rents = rentRepository.findAll().stream().filter(p -> p.getClientId()==id).collect(Collectors.toList());
        rents.stream().forEach(p -> rentRepository.deleteById(p.getId()));
        clientRepository.deleteById(id);
        log.trace("deleteClient - method finished");
    }
}
