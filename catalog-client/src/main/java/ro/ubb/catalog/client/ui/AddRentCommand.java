package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.validators.MovieProjectException;
import ro.ubb.catalog.web.dto.RentDto;

import java.util.Scanner;

public class AddRentCommand implements Command {
    public AddRentCommand() {}

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Movie Id: ");
        int movieId = scanner.nextInt();
        System.out.println("Client Id: ");
        int clientId = scanner.nextInt();
        try{
            restTemplate.postForObject(
                    URL + "/rents",
                    new RentDto(movieId, clientId),
                    RentDto.class);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
