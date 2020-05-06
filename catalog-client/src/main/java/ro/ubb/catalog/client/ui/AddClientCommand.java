package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.validators.MovieProjectException;
import ro.ubb.catalog.web.dto.ClientDto;

import java.util.Scanner;

public class AddClientCommand implements Command {

    public AddClientCommand() { }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Age: ");
        int age = scanner.nextInt();
        try {
            restTemplate.postForObject(
                    URL + "/clients",
                    new ClientDto(name, age),
                    ClientDto.class);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
