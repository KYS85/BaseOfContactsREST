package project;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.contacts.ContactsInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class ApiHandler implements HttpHandler {

    //Scanner in = new Scanner(System.in);

    public static boolean isAdd;
    public static boolean isEdit;

    public static String typeApi = "";
    public static String nameApi = "";
    public static String surnameApi = "";
    public static String birthApi = "";
    public static String genderApi = "";
    public static String numberApi = "";
    public static String orgAdressApi = "";

    private ContactsInterface contactsInterface;

    public ApiHandler(ContactsInterface contactsInterface) {
        this.contactsInterface = contactsInterface;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //String fileName = "HtmlPage.html";
        //String pathToFile = "/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/";
        typeApi = "";
        numberApi = "";
        surnameApi = "";
        birthApi = "";
        genderApi = "";
        nameApi = "";
        orgAdressApi = "";
        //
        // получаем тело запроса POST
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
// From now on, the right way of moving from bytes to utf-8 characters:
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();
        // The resulting string is: buf.toString()
// and the number of BYTES (not utf-8 characters) from the body is: buf.length()
        System.out.println(" to String income --> " + buf);
        //String decodedURL = URLDecoder.decode(buf.toString(), "UTF-8");
        //nameApi = buf.substring(buf.indexOf("=") +1); //decodedURL.substring(decodedURL.indexOf("=") +1);  // присваиваем имя из тела POSTr


        //System.out.println("RESpoNsE = " + exchange.getRequestBody().readAllBytes());
        String requestURI = exchange.getRequestURI().toString();
        String output = "Api in progress"; //new String(Files.readAllBytes(Paths.get(pathToFile + fileName)));
        String json = "";

        if (requestURI.contains("/api/delContact")) {
            output = "Api in progress: delete contact from list" +
                    "<p> Back to the list: <a href=\"http://localhost:8080/api/list\"> list </a> </p>";
            //api/delContact?id=1  - удалить контакт с индексом 1
            String keys = requestURI.substring((requestURI.indexOf('?') + 1));
            System.out.println("api keys: " + keys);
            String[] keyValuesPairs = keys.split("=");
            System.out.println("del: key1 = " + keyValuesPairs[0] + " value1 = " + keyValuesPairs[1]);
            try {
                contactsInterface.removeContact(Integer.parseInt(keyValuesPairs[1]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                ;
            }
            System.out.println("\n contact deleted, to continue press 5 and return");
        }

        if (requestURI.contains("/api/addContact")) {
            isAdd = true;
            output = "Api in progress: add contact";
            /*
            String typeApi = "";
            String nameApi = "";
            String surnameApi = "";
            String birthApi = "";
            String genderApi = "";
            String number = "";
            String orgAdressApi = "";
             */
            ///api/addContact?type=person&name=Adam&surname=Sendler&birth=1988.01.21&gender=M&number=+375(13)23-32-43
            String keys = requestURI.substring((requestURI.indexOf('?') + 1));
            keys = URLDecoder.decode(keys, "UTF-8") + buf;  // decode rus in url & plus buf - body of POST from Html
            System.out.println("api keys (/api/addContact): " + keys);
            String[] keyValuesPairs = keys.split("&");
            for (int i = 0; i < keyValuesPairs.length; i++) {
                System.out.println("add: key value pair " + (i + 1) + " = " + keyValuesPairs[i]);
                if (keyValuesPairs[i].startsWith("type") && keyValuesPairs[i].contains("person")) {
                    typeApi = "person";
                } else if (keyValuesPairs[i].contains("org")) {
                    typeApi = "org";
                }
                if (keyValuesPairs[i].startsWith("name")) {
                    nameApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
                if (keyValuesPairs[i].startsWith("surname") && "person".equals(typeApi)) {
                    surnameApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
                if (keyValuesPairs[i].startsWith("birth") && "person".equals(typeApi)) {
                    birthApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
                if (keyValuesPairs[i].startsWith("gender") && "person".equals(typeApi)) {
                    genderApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
                if (keyValuesPairs[i].startsWith("number")) {
                    numberApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
                if (keyValuesPairs[i].startsWith("orgAddress") && "org".equals(typeApi)) {
                    orgAdressApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                }
            }

            System.out.println("type = " + typeApi);
            System.out.println("name = " + nameApi);
            System.out.println("surname = " + surnameApi);
            System.out.println("birth = " + birthApi);
            System.out.println("gender = " + genderApi);
            System.out.println("number = " + numberApi);
            System.out.println("orgAdress = " + orgAdressApi);

            try {
                if ("org".equals(typeApi)) contactsInterface.addOrg();
                if ("person".equals(typeApi)) contactsInterface.addPerson();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("\n (contact added) To continue press 4 and enter");
            isAdd = false;
        }

        if ("/api/list".equals(requestURI)) {
            System.out.println("\nList of contacts from app ---> requested from Api "); //+ Methods.getCoList());
            //output = ExperimentList.json;

            try {
                json = new Gson().toJson(contactsInterface.getContactList()); // = ExperimentList.json;
                String fileName = "HtmlPage.html";
                String pathToFile = "/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/";
                output = new String(Files.readAllBytes(Paths.get(pathToFile + fileName)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(json + "\nList posted \nto continue work with Contacts type \" continue \" and pres return` ");
        }

        if (requestURI.contains("/api/getContact")) {
            String[] keyStr = requestURI.split("/api/getContact/");
            int id = Integer.parseInt(keyStr[1]) - 1;

            try {
                output = contactsInterface.getContactInfo(id);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (requestURI.contains("/api/edit")) {
            String id = "";

            isEdit = true;
            output = "Api in progress: edit +\n" +
                    "<p> Back to the list: <a href=\"http://localhost:8080/api/list\"> list </a> </p>";
            //http://localhost:8080/api/edit
            //http://localhost:8080/api/edit?id=1&name=Adam

            ///api/addContact?type=person&name=Adam&surname=Sendler&birth=1988.01.21&gender=M&number=+375(13)23-32-43
            String keys = requestURI.substring((requestURI.indexOf('?') + 1));
            keys = URLDecoder.decode(keys, "UTF-8") + buf;  // decode rus in url & plus buf - body of POST from Html
            System.out.println("api keys (/api/edit): " + keys);
            String[] keyValuesPairs = keys.split("&");
            for (int i = 0; i < keyValuesPairs.length; i++) {
                System.out.println("add: key value pair " + (i + 1) + " = " + keyValuesPairs[i]);
                if (keyValuesPairs[i].startsWith("id")) {
                    id = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("id= " + id);
                }
                if (keyValuesPairs[i].startsWith("surname")) {
                    surnameApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("surname= " + surnameApi);
                    try {
                        contactsInterface.updSurnameOrAddrApi(Integer.parseInt(id) - 1, surnameApi);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (keyValuesPairs[i].startsWith("orgAddress")) {
                    orgAdressApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("orgAddress= " + orgAdressApi);
                    try {
                        contactsInterface.updSurnameOrAddrApi(Integer.parseInt(id) - 1, orgAdressApi);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (keyValuesPairs[i].startsWith("name")) {
                    nameApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("name= " + nameApi);
                    try {
                        contactsInterface.updNameApi(Integer.parseInt(id) - 1, nameApi);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (keyValuesPairs[i].startsWith("number")) {
                    numberApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("number= " + numberApi);
                    try {
                        contactsInterface.updNumberApi(Integer.parseInt(id) - 1, numberApi);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (keyValuesPairs[i].startsWith("gender")) {
                    genderApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("gender= " + genderApi);
                    try {
                        contactsInterface.updGenderApi(Integer.parseInt(id) - 1, genderApi);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (keyValuesPairs[i].startsWith("birth")) {
                    birthApi = keyValuesPairs[i].substring((keyValuesPairs[i].indexOf('=') + 1));
                    System.out.println("birth= " + birthApi);
                    try {
                        contactsInterface.updBirthApi(Integer.parseInt(id) - 1, birthApi);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }


            System.out.println("\n (contact edited) To continue press 4 and enter");
            isEdit = false;

        }
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        byte[] bytes = output.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}








/*
                output = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title> MyPage </title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "</h1> Привет МИР!! </h1>\n" +
                        "</p> ================================== </p>\n" +
                        "</p> Вставка этого json как строка в HTML:  </p>\n" +
                        "</p>  >>>" + json + "<<< </p>\n" +
                        "<script> " +
                        " var a = 7; var b = 3; var c = a % b;  console.log('a % b = ' + c); " +
                        "console.log('ggfgfgf' );" +
                        "console.log('ggfgfgf' );" +
                        //"var obj = JSON.parse("+ json + ");  " +
                        " console.log('myArrStr' );  const myArrStr = JSON.stringify("+ json + ");  console.log(myArrStr );" +
                        "console.log('индекс 0 = ' + "+ json + "[0] ); document.write(' <br \\/> индекс 1 = ' + "+ json + "[1] +  ' <br \\/> ');" +
                        "document.write(' <br \\/>  индекс 2 = ' + "+ json + "[2] +  ' <br \\/> <br \\/> ');" +
                        "document.write(' <br \\/> Переберём переданный массив контактов JSON: <br \\/>'); " +
                        "for (let l = 0; l < "+ json +".length; l++) {document.write("+ json + "[l] + ' <br \\/> ') } document.write(' <br \\/> ');"
                        +
                        //" alert('JavaScript is there');\n" +
                        " document.write('Value of 7 % 3 is -> ' + c + '    <br \\/>   ');" +
                        " document.write('JSON строкой в JavaScript ->   ');" +
                        " document.write("+ json + " +  ' <br \\/>  <br \\/> ');\n" +
                        "const xhr = new XMLHttpRequest();" +
                        "xhr.open(\"GET\", \"http://localhost:8080/index/\");" +
                        "xhr.onload = () => {\n" +
                        "    if (xhr.status == 200) {                // если код ответа 200\n" +
                        "        console.log('Получиено от сервера что-то Status: ', xhr.statusText);      // выводим полученный ответ на консоль браузера\n" +
                        "    } else {                                // иначе выводим текст статуса\n" +
                        "        console.log(\"Server response: \", xhr.statusText);\n" +
                        "    }\n" +
                        "};" +
                        "xhr.send();" +
                        "document.write(' <br \\/>  Received: Something is came =)');" +
                        " </script>\n" +
                        "<p> end =) </p>\n" +
                        "<p> <img scr='/Users/spesu/IdeaProjects/ContactsREST/src/main/java/project/listening.png' alt=\"альтернативный текст\"> </p>" +
                        "<img src=\"/index/response\", alt = 'request from server'>" +
                        "</body>\n" +
                        "</html>";
*/
