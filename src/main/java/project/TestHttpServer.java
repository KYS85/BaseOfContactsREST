package project;

import com.sun.net.httpserver.*;
import project.contacts.ContactManager;
import project.contacts.Main;
import project.contacts.Methods;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import project.contacts.ContactsInterface;

public class TestHttpServer {

    //public static ContactManager manager;
    static ContactsInterface contactsInterface;

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        //http://localhost:8080/index

        contactsInterface = new ContactManager();

        server.createContext("/index", new IndexHandler());
        server.createContext("/api", new ApiHandler(contactsInterface));
        server.createContext("/content", new ContentHandler(contactsInterface));

        server.setExecutor(null);
        server.start();
        System.out.println("Server Starts");

        ContactManager manager = new ContactManager();
        manager.ManageContacts();


        System.out.print("To stop Server type \"stop\": ");

        while (in.hasNext()) {


            System.out.println("Hello!!!");

            if (in.nextLine().equals("stop")) {
                break;
            }
        }
        server.stop(0);
    }


    public static void openHello1(HttpExchange exchange) throws IOException {
        String fileName = "HtmlPage.html";
        String pathToFile = "/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/";
        String output = new String(Files.readAllBytes(Paths.get(pathToFile + fileName)));

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        byte[] bytes = output.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    } // end of /localhost/hello1
}

/* InputStream is = exchange.getRequestBody();
            String streamOutput = "";
            System.out.println("output from browser -> " + streamOutput);
            is.close();

 */