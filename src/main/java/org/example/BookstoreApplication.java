package org.example;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("")
public class BookstoreApplication extends ResourceConfig {
    public BookstoreApplication() {
        // Scan for resource classes and providers
        packages("org.example.resource", "org.example.exception");
    }
}