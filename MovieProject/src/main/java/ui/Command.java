package ui;

import service.*;

import java.util.Scanner;

public interface Command {
    void execute(Scanner scanner, ServiceMovieInterface serviceMovie, ServiceClientInterface serviceClient, ServiceRentInterface serviceRent);
}
