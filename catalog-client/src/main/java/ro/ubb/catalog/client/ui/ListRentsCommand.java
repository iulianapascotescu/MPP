package ro.ubb.catalog.client.ui;


import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.RentsDto;

import java.util.Scanner;

public class ListRentsCommand implements Command {

    public ListRentsCommand() {
    }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println(restTemplate.getForObject(URL + "/rents", RentsDto.class));
    }
}
