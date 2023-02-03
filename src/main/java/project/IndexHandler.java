package project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String fileName = "redDel1.jpg"; //"Anime.jpg"; //"listening.png"; //
        String pathToFile = "/Users/spesu/Java/HTML/";// "/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/";

        exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
        long bytes = Files.size(Path.of(pathToFile + fileName));

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
