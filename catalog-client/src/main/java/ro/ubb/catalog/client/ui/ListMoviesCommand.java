package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.MoviesDto;

import java.util.Scanner;

public class ListMoviesCommand implements Command {
    public ListMoviesCommand() { }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println(restTemplate.getForObject(URL + "/movies", MoviesDto.class));
    }
}
