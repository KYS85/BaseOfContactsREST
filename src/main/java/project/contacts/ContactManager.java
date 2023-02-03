package project.contacts;

import project.ApiHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManager implements Serializable, ContactsInterface {
    //Scanner in = new Scanner(System.in);

    public String command = "-1";
    static Contact contact;
    static ContactList cl;
    static List<Contact> contactsList;

    public void ManageContacts() throws IOException, ClassNotFoundException { // Contact manager
        String path = "src/main/resources/contactBookJ.data";
/*
        Contact contact;
        ContactList cl = new ContactList();
        List<Contact> contactsList = cl.contactList;  // assign to list deserialized list
        //List<Contact> contactsList = cl.contactListNS;

         */

        cl = new ContactList();
        contactsList = cl.contactList;

        ContactFactory personFactory = new PersonFactory();
        OrganizationFactory organizationFactory = new OrganizationFactory(); /* write your code here */

        int numOfrecords = contactsList.size();

        while (!command.equals("exit")) {

            System.out.println("command = " + command);
            SerializationUtils.serialize(contactsList, path);  // serialize list with changes
            System.out.println("[menu] Enter action (add, list, search, count, exit): ");
            //System.out.print("Enter action (add, remove, edit, count, info, exit): ");


            //command = in.nextLine();
            command = command == null ? "null" : Helper.getUserInput();

            switch (command) {
                case "search":
                    String whatToDo = "";
                    while (true) {  // loop for searching
                        System.out.print("Enter search query: ");
                        String query = Helper.getUserInput(); //in.nextLine();
                        int count = 1;
                        int rec = 0;
                        int[] recAr = new int[contactsList.size() + 1]; // write found positions to array
                        for (int i = 0; i < contactsList.size(); i++) {   // loop to match query
                            if (contactsList.get(i).getDescription().toLowerCase().contains(query.toLowerCase())) {
                                System.out.println(count + ". " + contactsList.get(i).getTitle());
                                recAr[rec] = i;
                                //System.out.println("recAr[rec] = " + recAr[rec]);
                                count++;
                                rec++;
                            }
                        }
                        System.out.println();  // new line after search result

                        System.out.print("[search] Enter action ([number], back, again): ");
                        whatToDo = Helper.getUserInput(); //in.next();
                        int editedLine = -1;
                        try {
                            if (Integer.valueOf(whatToDo).getClass().toString().contains("Integer")) {
                                editedLine = Integer.parseInt(whatToDo) - 1;
                                System.out.println(contactsList.get(recAr[editedLine]).getDescription());
                            }
                        } catch (Exception ignored) {
                        }


                        //System.out.println();  // new line after selection
                        if (whatToDo.equals("back")) {
                            //in.nextLine();              // 1 diabled
                            break;
                        }

                        if (whatToDo.equals("again")) {
                            //in.nextLine();                // 2 diabled
                            continue;
                        }
                        System.out.print("[record] Enter action (edit, delete, menu): ");

                        //in.nextLine();  // fix double sout    // 3 diabled
                        String whatDoNext = Helper.getUserInput(); //in.next();
                        //in.nextLine();  // fix double sout            // 4 diabled
                        //System.out.println("whatDoNext " +whatDoNext);
                        if (whatDoNext.equals("menu")) {
                            break;
                        } else {
                            // start of trash work
                            switch (whatDoNext) {
                                case "delete":   // edit this method to selected and return
                                    if (numOfrecords == 0) {
                                        System.out.println("No records to remove!\n");
                                    } else {
                                        contactsList.remove(editedLine);
                                        numOfrecords--;
                                        System.out.println("The record removed!\n");
                                    }
                                    break;

                                case "edit":    // edit this method to selected and return
                                    Methods.editInSearch(contactsList, contactsList.size(), editedLine);
                                    // this is something wrong  !!!!!!!!!!!
                            }
                            break;
                            // trash switch end
                        }
                        // break; // to enter to main menu
                        //in.nextLine();   // fix double sout
                    }
                    break;
                // continue;   // to return to menu from search

                case "add":
                    System.out.print("Enter the type (person, organization): ");
                    //if (in.next().equals("person")) {
                    if (Helper.getUserInput().equals("person")) {
                        contact = personFactory.createContact();
                        contactsList.add(contact);
                        //SerializationUtils.serialize(contactsList, path);
                    } else {
                        contact = organizationFactory.createContact(); /* write your code here */
                        contactsList.add(contact);
                    }
                    numOfrecords++;
                    System.out.println("The record added. \n");
                    break;
                // maby later kill remove and edit below
                case "remove":
                    Methods.remove(contactsList, numOfrecords, 0); // from main menu
                    break;

                case "edit": // edit from main menu
                    Methods.edit(contactsList, contactsList.size());
                    break;

                case "count":
                    System.out.printf("The Phone Book has %d records.\n\n", contactsList.size());
                    break;

                case "list":   // refactored
                    Methods.getList(contactsList, numOfrecords);
                    System.out.println();

                    System.out.print("[list] Enter action ([number], back): ");
                    // check input for int
                    String input = Helper.getUserInput(); // in.next();

                    int inputInt = 0;
                    String inputStr = "";
                    boolean isBack = false;

                    try {
                        if (Integer.valueOf(input).getClass().toString().contains("Integer")) {
                            inputInt = Integer.parseInt(input);
                        }
                    } catch (Exception ignored) {
                        inputStr = input;
                        isBack = true;
                    }
                    // end of checking int  and breaking if not
                    if (isBack) {
                        //in.nextLine();        // 5 diabled
                        break;
                    }  // write condition to input like equals("back")
                    int record = inputInt - 1;
                    System.out.println(contactsList.get(record).getDescription());

                    while (true) {
                        System.out.print("[record] Enter action (edit, delete, menu): ");
                        String variant = Helper.getUserInput();  //in.next();
                        // write chose variants logic
                        if (variant.equals("edit")) {
                            Methods.editInSearch(contactsList, numOfrecords, record);
                            System.out.println(contactsList.get(record).getDescription());
                            break;
                        }

                        if (variant.equals("delete")) {
                            contactsList.remove(record);
                            numOfrecords--;
                            System.out.println("The record removed!\n");
                            //in.nextLine();            // 6 diabled
                            break;
                        }

                        if (variant.equals("menu")) {
                            //in.nextLine();        // 7 diabled
                            break;
                        }
                    }

                    //in.nextLine();  // fix double sout
                    break;
            }
            //in.nextLine();  // fix souble sout
        }

        //in.close();
    }

    // method check and return valid number or ""
    private String isValidNumber(String number) {

        String numberFormatFilter = "\\+?(\\([0-9a-zA-Z]+\\)|[0-9a-zA-Z]+([ -][(][0-9a-zA-Z]{2,}[)])?)([ -][0-9a-zA-Z]{2,})*";
        Pattern pattern = Pattern.compile(numberFormatFilter);

        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) System.out.println("Wrong number format!");
        return matcher.matches() ? number : "[no number]";
    }

    public static String getNameApi() {
        return ApiHandler.isAdd ? ApiHandler.nameApi : Helper.getUserInput();
    }

    public static String getSurNameApi() {
        return ApiHandler.isAdd ? ApiHandler.surnameApi : Helper.getUserInput();
    }

    public static String getGenderApi() {
        return ApiHandler.isAdd ? ApiHandler.genderApi : Helper.getUserInput();
    }

    public static String getNumApi() {
        return ApiHandler.isAdd ? ApiHandler.numberApi : Helper.getUserInput();
    }

    public static String getBirthApi() {
        return ApiHandler.isAdd ? ApiHandler.birthApi : Helper.getUserInput();
    }

    public static String getOrgAddress() {
        return ApiHandler.isAdd ? ApiHandler.orgAdressApi : Helper.getUserInput();
    }


    public static String inputCollector(String key) {
        String inputLine = null;
        if ("name".equals(key) && ApiHandler.isAdd) {
            //inputLine = ApiHandler.nameApi;
            ApiHandler.isAdd = false;
            return ApiHandler.nameApi;
        }
        //inputLine = Helper.getUserInput();
        if ("surname".equals(key) && ApiHandler.isAdd) {
            ApiHandler.isAdd = false;
            inputLine = ApiHandler.surnameApi;
        }
        //inputLine = ApiHandler.surnameApi;
        inputLine = Helper.getUserInput();
        //if ("birth".equals(key)) inputLine = ApiHandler.birthApi;
        //if ("gender".equals(key)) inputLine = ApiHandler.genderApi;
        //if ("number".equals(key)) inputLine = ApiHandler.numberApi;
        //if ("address".equals(key))inputLine = ApiHandler.orgAdressApi;
        ApiHandler.isAdd = false;
        return inputLine;
    }

    @Override
    public List<String> getContactList() throws IOException, ClassNotFoundException {
        /*String command = "list";
        String path = "/Users/spesu/java/dir/contactBookJ.data";
        Contact contact;

         */
        //ContactList cl = new ContactList();
        List<Contact> contactsList = cl.contactList;  // assign to list deserialized list

        return Methods.getList(contactsList, contactsList.size());
    }

    @Override    //method for API
    public String getContactInfo(int idx) throws IOException, ClassNotFoundException {
        List<Contact> contactsList = cl.contactList;  // assign to list deserialized list

        return contactsList.get(idx).getDescription();
    }

    @Override
    public List<Contact> removeContact(int idxToDel) throws IOException, ClassNotFoundException {
        contactsList = cl.contactList;
        Methods.remove(contactsList, contactsList.size(), idxToDel);
        return contactsList;
    }

    @Override
    public List<Contact> addPerson() {
        PersonFactory personFactory = new PersonFactory();
        contact = personFactory.createContact();
        contactsList.add(contact);
        return contactsList;
    }

    @Override
    public List<Contact> addOrg() {
        OrganizationFactory organizationFactory = new OrganizationFactory();
        contact = organizationFactory.createContact();
        contactsList.add(contact);
        return contactsList;
    }

    @Override
    public List<Contact> updNameApi(int idx, String name) {
        contactsList.get(idx).updateNameApiMain(name);
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
        if (contactsList.get(idx) instanceof Organization) {
            ((Organization) contactsList.get(idx)).timeUpd = now.toString();
        } else {
            ((Person) contactsList.get(idx)).timeUpd = now.toString();
        }

        return contactsList;
    }

    @Override
    public List<Contact> updSurnameOrAddrApi(int idx, String surnameOrAddr) {

        contactsList.get(idx).updateSurnameOrAddrApiMain(surnameOrAddr);
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
        if (contactsList.get(idx) instanceof Organization) {

            ((Organization) contactsList.get(idx)).timeUpd = now.toString();
        } else {
            ((Person) contactsList.get(idx)).timeUpd = now.toString();
        }

        return contactsList;
    }

    @Override
    public List<Contact> updNumberApi(int idx, String num) {
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
        if (contactsList.get(idx) instanceof Organization) {

            ((Organization) contactsList.get(idx)).timeUpd = now.toString();
        } else {
            ((Person) contactsList.get(idx)).timeUpd = now.toString();
        }
        contactsList.get(idx).number.setNum(num);
        return contactsList;
    }

    @Override
    public List<Contact> updGenderApi(int idx, String gender) {
        contactsList.get(idx).updateGenderApiMain(gender);
        return contactsList;
    }

    @Override
    public List<Contact> updBirthApi(int idx, String birth) {
        contactsList.get(idx).updateBirthApiMain(birth);
        return contactsList;
    }


}

