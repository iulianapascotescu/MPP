package ui;

import domain.Rent;
import domain.validators.MovieProjectException;
import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class AddRentCommand implements Command {
    public AddRentCommand() {}

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        //System.out.println("Rent Id: ");
        //int rentId = scanner.nextInt();
        System.out.println("Movie Id: ");
        int movieId = scanner.nextInt();
        System.out.println("Client Id: ");
        int clientId = scanner.nextInt();
        try{
            serviceRent.addRent(new Rent(movieId,clientId));
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
