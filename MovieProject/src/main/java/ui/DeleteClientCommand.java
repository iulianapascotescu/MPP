package ui;

import domain.validators.MovieProjectException;
import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class DeleteClientCommand implements Command {
    public DeleteClientCommand() {}

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        System.out.println("Id: ");
        int id = scanner.nextInt();
        try{
            serviceClient.deleteClient(id);
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
