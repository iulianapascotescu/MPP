package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.validators.MovieProjectException;

import java.util.Scanner;

public class DeleteClientCommand implements Command {
    public DeleteClientCommand() {}

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Id: ");
        int id = scanner.nextInt();
        try{
            restTemplate.delete(URL + "/clients/{id}", id);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
