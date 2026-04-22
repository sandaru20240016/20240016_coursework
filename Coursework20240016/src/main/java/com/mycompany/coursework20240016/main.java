
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sanda
 */


public class main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        // Scan your project package for resources
        final ResourceConfig config = new ResourceConfig()
                .packages("com.mycompany.coursework20240016");

        // Start server
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        System.out.println("Server started at " + BASE_URI + "api/v1");
        System.out.println("Press ENTER to stop the server...");

        System.in.read();
        server.shutdownNow();
    }
}