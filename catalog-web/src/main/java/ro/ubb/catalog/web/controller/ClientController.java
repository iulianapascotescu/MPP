package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.service.ClientServiceInterface;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;
import ro.ubb.catalog.web.dto.MovieDto;

@RestController
public class ClientController {
    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientServiceInterface clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ClientsDto getClients(){
        log.trace("ClientsDto getClients - method entered");
        ClientsDto clientsDto = new ClientsDto(clientConverter
                .convertModelsToDtos(clientService.getAllClients()));
        log.trace("ClientsDto getClients - method finished");
        return clientsDto;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ClientDto saveClient(@RequestBody ClientDto clientDto) {
        log.trace("ClientDto saveClient: clientDto={} - method entered", clientDto);
        ClientDto clientDtoSaved = clientConverter.convertModelToDto(clientService.saveClient(
                clientConverter.convertDtoToModel(clientDto)));
        log.trace("ClientDto saveClient - method finished");
        return clientDtoSaved;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    public ClientDto updateClient(@PathVariable Integer id, @RequestBody ClientDto clientDto) {
        log.trace("ClientDto updateClient: clientDto={} - method entered", clientDto);
        ClientDto clientDtoUpdated = clientConverter.convertModelToDto(clientService.updateClient(
                clientConverter.convertDtoToModel(clientDto)));
        log.trace("ClientDto updateClient - method finished");
        return clientDtoUpdated;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable Integer id){
        log.trace("ResponseEntity<?> deleteClient: id={} - method entered", id);
        clientService.deleteClient(id);
        log.trace("ResponseEntity<?> deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
