package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.web.controller.ClientController;
import ro.ubb.catalog.web.controller.MovieController;
import ro.ubb.catalog.web.controller.RentController;

import java.util.Scanner;

public interface Command {
    void execute(Scanner scanner, String URL, RestTemplate restTemplate);
}
