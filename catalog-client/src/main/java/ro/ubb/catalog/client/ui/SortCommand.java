package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.MoviesDto;

import java.util.Scanner;

public class SortCommand implements Command {

    public SortCommand() {
    }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println(restTemplate.getForObject(URL + "/movies/sorted", MoviesDto.class));
    }
}
