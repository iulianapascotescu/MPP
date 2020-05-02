package ui;

import domain.Movie;
import domain.validators.MovieProjectException;
import service.ServiceClientInterface;
import service.ServiceMovieInterface;
import service.ServiceRentInterface;

import java.util.Scanner;

public class AddMovieCommand implements Command{
    public AddMovieCommand() {}

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent) {
        //System.out.println("Id: ");
        //int id = scanner.nextInt();
        System.out.println("Title: ");
        String title = scanner.next();
        System.out.println("Genre: ");
        String genre = scanner.next();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        try{
            serviceMovie.addMovie(new Movie(title,genre,year));
        }
        catch(MovieProjectException e) {
            System.out.println(e.toString());
        }
    }
}
