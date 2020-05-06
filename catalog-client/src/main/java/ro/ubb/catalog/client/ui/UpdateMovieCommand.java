package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.validators.MovieProjectException;
import ro.ubb.catalog.web.dto.MovieDto;

import java.util.Scanner;

public class UpdateMovieCommand implements Command {
    public UpdateMovieCommand() {}

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Id: ");
        int id = scanner.nextInt();
        System.out.println("Title: ");
        String title = scanner.next();
        System.out.println("Genre: ");
        String genre = scanner.next();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        try {
            MovieDto movie = new MovieDto(title, genre, year);
            movie.setId(id);
            restTemplate.put(URL + "/movies/{id}", movie, id);
        } catch (MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
