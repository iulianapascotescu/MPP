package ui;

import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class FindMostRentedMovieCommand implements Command{
    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        System.out.println("The most rented movie is " + serviceRent.findMostRentedMovie());
    }
}
