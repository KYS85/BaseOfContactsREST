package project;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.contacts.ContactsInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContentHandler implements HttpHandler {

    private ContactsInterface contactsInterface;
    public ContentHandler(ContactsInterface contactsInterface) {
        this.contactsInterface = contactsInterface;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        String json = "";

        if ("/content/json".equals(requestURI)) {
            System.out.println("\nList of contacts from app ---> requested from Api ");
            try {
                json = new Gson().toJson(contactsInterface.getContactList());

                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                byte[] bytes1 = json.getBytes();
                exchange.sendResponseHeaders(200, bytes1.length);

                OutputStream os1 = exchange.getResponseBody();
                os1.write(bytes1);
                os1.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(json + "\nList requested from Content.... to  \" continue \" and pres return` ");
        }

        String fileName = "listening.png"; // "Anime.jpg"; //
        String pathToFile = "/Users/spesu/Java/HTML/";// "/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/";

        if ("/content/anime".equals(requestURI)) {
            fileName =  "Anime.jpg"; //
        }
        exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
        long bytes = Files.size(Path.of(pathToFile+fileName));

        exchange.sendResponseHeaders(200, bytes);

        try (FileInputStream fis = new FileInputStream(pathToFile + fileName)) {
            try (OutputStream os = exchange.getResponseBody()) {
                copy(fis, os);
            }
        }
    }

    void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) != -1) {
            target.write(buf, 0, length);
        }
    }
}
