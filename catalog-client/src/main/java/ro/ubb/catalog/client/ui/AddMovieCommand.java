package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.validators.MovieProjectException;
import ro.ubb.catalog.web.dto.MovieDto;

import java.util.Scanner;

public class AddMovieCommand implements Command{
    public AddMovieCommand() {}

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Title: ");
        String title = scanner.next();
        System.out.println("Genre: ");
        String genre = scanner.next();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        try{
            restTemplate.postForObject(
                    URL + "/movies",
                    new MovieDto(title, genre, year),
                    MovieDto.class);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
