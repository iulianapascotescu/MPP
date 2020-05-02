package ui;

import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class ListMoviesCommand implements Command {
    public ListMoviesCommand() { }

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        serviceMovie.getAllMovies().forEach(System.out::println);
    }
}
