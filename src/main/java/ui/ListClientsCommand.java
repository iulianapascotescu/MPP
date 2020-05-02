package ui;

import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class ListClientsCommand implements Command {
    public ListClientsCommand() { }

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        serviceClient.getAllClients().forEach(System.out::println);
    }
}
