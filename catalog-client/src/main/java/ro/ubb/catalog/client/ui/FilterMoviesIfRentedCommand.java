package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.dto.RentsDto;

import java.util.Arrays;
import java.util.Scanner;

public class FilterMoviesIfRentedCommand implements Command {

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println(Arrays.toString(restTemplate.getForObject(URL + "/rents/filter", String[].class)));
    }
}
