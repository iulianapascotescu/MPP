package ui;

import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class ListRentsCommand implements Command {
    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        serviceRent.getAllRents().forEach(System.out::println);
    }
}
