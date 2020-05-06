package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.catalog.client.ui.Console;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.catalog.client.config"
                );

        context.getBean(Console.class).runConsole();
    }
}
