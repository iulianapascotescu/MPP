package ro.ubb.catalog.client.ui;


import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.ClientsDto;

import java.util.Scanner;

public class ListClientsCommand implements Command {
    public ListClientsCommand() { }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println(restTemplate.getForObject(URL +"/clients", ClientsDto.class));
    }
}
