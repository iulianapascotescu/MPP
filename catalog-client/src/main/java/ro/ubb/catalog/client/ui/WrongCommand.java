package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class WrongCommand implements Command {

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.out.println("Wrong command");
    }
}
