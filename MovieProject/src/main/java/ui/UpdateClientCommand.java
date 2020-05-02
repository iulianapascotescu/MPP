package ui;

import domain.Client;
import domain.validators.MovieProjectException;
import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class UpdateClientCommand implements Command {
    public UpdateClientCommand() { }

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        System.out.println("Id: ");
        int id = scanner.nextInt();
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Age: ");
        int age = scanner.nextInt();
        try{
            serviceClient.updateClient(new Client(id,name,age));
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
