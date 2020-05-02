package service;

import domain.Client;
import domain.Rent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.dbRepository.ClientDBRepository;
import repository.dbRepository.MovieDBRepository;
import repository.dbRepository.RentDBRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceClient implements ServiceClientInterface{
    public static final Logger log = LoggerFactory.getLogger(ServiceClient.class);
    @Autowired
    private MovieDBRepository repositoryMovie;
    @Autowired
    private ClientDBRepository repositoryClient;
    @Autowired
    private RentDBRepository repositoryRent;

    public ServiceClient() {
    }

    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients - method entered");
        return repositoryClient.findAll();
    }

    @Override
    public void addClient(Client client) {
        log.trace("addClient - method entered: client={}", client);
        repositoryClient.save(client);
        log.trace("addClient - method finished");
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
        log.trace("updateClient - method entered: client={}", client);
        repositoryClient.findById(client.getId())
                .ifPresent(s -> {
                    s.setName(client.getName());
                    s.setAge(client.getAge());
                    log.debug("updateClient - updated: s={}", s);
                });
        log.trace("updateClient - method finished");
    }

    @Override
    public void deleteClient(Integer id) {
        log.trace("deleteClient - method entered: id={}", id);
        List<Rent> rents = repositoryRent.findAll().stream().filter(p -> p.getClientId()==id).collect(Collectors.toList());
        rents.stream().forEach(p -> repositoryRent.deleteById(p.getId()));
        repositoryClient.deleteById(id);
        log.trace("deleteClient - method finished");
    }
}
