import domain.Client;
import domain.Movie;
import domain.Rent;
import domain.validators.ClientValidator;
import domain.validators.MovieValidator;
import domain.validators.RentValidator;
import domain.validators.Validator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;
import repository.dbRepository.SortingRepository;
import service.*;
import ui.Console;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, SQLException {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "configuration"
                );

        context.getBean(Console.class).runConsole();

        System.out.println("bye");
    }
}
