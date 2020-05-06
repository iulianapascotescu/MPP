package ro.ubb.catalog.client.ui;

import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class ExitCommand implements Command{
    public ExitCommand() { }

    @Override
    public void execute(Scanner scanner, String URL, RestTemplate restTemplate) {
        System.exit(0);
    }
}
