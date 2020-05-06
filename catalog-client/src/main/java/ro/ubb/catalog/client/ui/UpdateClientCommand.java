package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.MovieProjectException;
import ro.ubb.catalog.web.dto.ClientDto;

import java.util.Scanner;

public class UpdateClientCommand implements Command {
    public UpdateClientCommand() { }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Id: ");
        int id = scanner.nextInt();
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Age: ");
        int age = scanner.nextInt();
        try{
            ClientDto client = new ClientDto(name, age);
            client.setId(id);
            restTemplate.put(URL + "/clients/{id}", client, id);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
