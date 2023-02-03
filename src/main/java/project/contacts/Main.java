
package project.contacts;

import project.ApiHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ContactList implements Serializable {   // class for serialization List
    String path = "/Users/spesu/java/dir/contactBookJ.data";
    List<Contact> contactList = (List<Contact>) SerializationUtils.deserialize(path);

    //List<Contact> contactList = new ArrayList<>();
    ContactList() throws IOException, ClassNotFoundException {
    }
}

public class Main {
    //public static List<Contact> contactList = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ContactManager manager = new ContactManager();
        manager.ManageContacts();
        System.out.println("CL main Main in app->"); // + Methods.getCoList());   //remove
    }
}

interface ContactFactory {
    Contact createContact();
}

class PersonFactory implements ContactFactory {

    @Override
    public Contact createContact() {   // changed to subclass
        PersonDetailsFactory detailsFactory = new PersonDetailsFactory();

        return new Person(detailsFactory);
    }
}

class OrganizationFactory implements ContactFactory {

    @Override
    public Contact createContact() {   //ContactDetailsFactory changed to OrganizationDetailsFactory
        OrganizationDetailsFactory detailsFactory = new OrganizationDetailsFactory();

        return new Organization(detailsFactory);
    }
}

interface ContactDetailsFactory {

    Number createNumber();

    String createName();

    String createOrgAddress();

    String createTimeCreated();

    String createTimeUpdated();
}

class PersonDetailsFactory implements ContactDetailsFactory {

    //Scanner in = new Scanner(System.in);

    public String createTimeCreated() {   // method add date of creation of contact
        LocalDateTime timeCreated = LocalDateTime.now().withNano(0).withSecond(0);
        return timeCreated.toString();
    }

    @Override
    public String createTimeUpdated() {
        LocalDateTime timeUpd = LocalDateTime.now().withNano(0).withSecond(0);
        return timeUpd.toString();
    }

    public String createGender() {
        System.out.print("Enter the gender (M, F): ");
        /*
        String gender = Helper.getUserInput();//in.nextLine();
        if (gender.isEmpty()) {          // here will be method to check valid gender
            System.out.println("Bad gender!");
        }
        return gender.isEmpty() ? "[no data]" : gender;  // enter name from console
         */
        return ContactManager.getGenderApi();
    }

    public String createBirth() {
        System.out.print("Enter the birth date: ");
        /*
        String birth = Helper.getUserInput(); //in.nextLine();
        if (birth.isEmpty()) {          // here will be method to check valid birthdate
            System.out.println("Bad birth date!");
        }
        return birth.isEmpty() ? "[no data]" : birth;  // enter name from console

         */
        return ContactManager.getBirthApi();
    }

    @Override
    public Number createNumber() {    // changes in class Number
        return new NumberPerson(); // dialog in class constructor
    }

    public String createName() {
        System.out.print("Enter the name: ");
        return ContactManager.getNameApi();
        //return Helper.getUserInput();
        //return ContactManager.inputCollector("name");  // enter name from console
    }

    public String createSurname() {
        System.out.print("Enter the surname: ");
        return ContactManager.getSurNameApi();
        //return Helper.getUserInput(); //ContactManager.inputCollector("surname"); //Helper.getUserInput();   //in.nextLine();  // enter surname from console
    }

    @Override   // later to fix it
    public String createOrgAddress() {
        return null;
    }
}

class OrganizationDetailsFactory implements ContactDetailsFactory {
    //Scanner in = new Scanner(System.in);

    @Override
    public Number createNumber() {
        return new NumberOrganization(); // dialog in class constructor   // fix in class
    }

    public String createName() {
        System.out.print("Enter the organization name: ");
        return ContactManager.getNameApi();//Helper.getUserInput();  //in.nextLine();  // enter name from console
    }

    public String createOrgAddress() {
        System.out.print("Enter the address: ");
        return ContactManager.getOrgAddress(); //Helper.getUserInput();  //in.nextLine();  // enter address from console
    }

    public String createTimeCreated() {   // method add date of creation of contact
        LocalDateTime timeCreated = LocalDateTime.now().withSecond(0).withNano(0);
        return timeCreated.toString();
    }

    @Override
    public String createTimeUpdated() {
        LocalDateTime timeUpd = LocalDateTime.now().withSecond(0).withNano(0);
        return timeUpd.toString();
    }
}

abstract class Contact implements Serializable {

    final boolean isPerson = false;  // One of the solutions is to create a final Boolean field isPerson in the base class.
    Number number;

    abstract String getDescription();

    abstract String getTitle();

    abstract String updateNameApiMain(String name);

    abstract String updateSurnameOrAddrApiMain(String name);

    abstract String updateBirthApiMain(String birth);

    abstract String updateGenderApiMain(String birth);

    @Override
    public String toString() {
        return "Number: " + number + "\n";
    }
}

class Person extends Contact implements Serializable {
    // Scanner fix!
    private String name;
    private String surname;
    private String birth;
    private String gender;
    private String timeCreated;
    String timeUpd;

    Person(PersonDetailsFactory detailsFactory) {
        super();
        name = detailsFactory.createName();
        surname = detailsFactory.createSurname();
        birth = detailsFactory.createBirth();
        gender = detailsFactory.createGender();
        number = detailsFactory.createNumber();
        timeCreated = detailsFactory.createTimeCreated();
        timeUpd = detailsFactory.createTimeUpdated(); // there is no update
    }


    /*
    Person(String name, String surname, String birth, String gender) {

        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
    }

     */

    public String updateName() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the name: ");
        return this.name = Helper.getUserInput(); //in.nextLine();
    }


    public String updateSurname() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the surname: ");
        return this.surname = Helper.getUserInput(); //in.nextLine();
    }

    public String updateBirth() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the birth date: ");
        return this.birth = Helper.getUserInput(); //in.nextLine();
    }

    public String updateGender() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the gender (M, F): ");
        return this.gender = Helper.getUserInput(); //in.nextLine();
    }

    void updateNumber() {
        System.out.print("Enter the number: ");
        this.number = new NumberPerson();
    }

    @Override
    public String getDescription() {
        return "Name: " + name + "\n  <br/>" +
                "Surname: " + surname + "\n  <br/>" +
                "Birth date: " + birth + "\n  <br/>" +
                "Gender: " + gender + "\n  <br/>" +
                super.toString() + " <br/>" + // number
                "Time created: " + timeCreated + "\n  <br/>" +
                "Time last edit: " + timeUpd + "\n  <br/>";   // <-- changing when edited
    }

    @Override
    String getTitle() {
        return name + " " + surname;
    }

    @Override
    String updateNameApiMain(String name) {
        System.out.print(this.name + " name upd in Api to " + name);
        return this.name = name;
    }

    @Override
    String updateSurnameOrAddrApiMain(String surname) {
        return this.surname = surname;
    }

    @Override
    String updateBirthApiMain(String birth) {
        return this.birth = birth;
    }

    @Override
    String updateGenderApiMain(String gender) {
        return this.gender = gender;
    }

}

class Organization extends Contact implements Serializable {

    private String orgName;
    private String orgAddress;
    private final String timeCreated;
    String timeUpd;


    Organization(ContactDetailsFactory detailsFactory) {
        super();
        orgName = detailsFactory.createName();
        orgAddress = detailsFactory.createOrgAddress();
        number = detailsFactory.createNumber();
        timeCreated = detailsFactory.createTimeCreated();
        timeUpd = detailsFactory.createTimeUpdated();  // not updated in creation

    }

    public String updateName() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the name: ");
        return orgName = Helper.getUserInput();  //in.nextLine();
    }

    public Number updateNumber() {
        System.out.print("Enter the number: ");
        return number = new NumberOrganization();
    }

    void updateAddress() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the address: ");
        this.orgAddress = Helper.getUserInput(); //in.nextLine();
    }

    @Override
    public String getDescription() {
        return "Organization name: " + orgName + "\n <br/>" +
                "Address: " + orgAddress + "\n <br/>" +
                super.toString() + " <br/>" + // number
                "Time created: " + timeCreated + "\n <br/>" +
                "Time last edit: " + timeUpd + "\n <br/>";    //<-- change time when upd
    }

    @Override
    String getTitle() {
        return orgName + " " + orgAddress;
    }

    @Override
    String updateNameApiMain(String orgName) {
        System.out.print(this.orgName + " name upd in Api to " + orgName);
        return this.orgName = orgName;
    }

    @Override
    String updateSurnameOrAddrApiMain(String orgAddress) {
        return this.orgAddress = orgAddress;
    }

    @Override
    String updateBirthApiMain(String birth) {
        return "Organization no Birth";
    }

    @Override
    String updateGenderApiMain(String birth) {
        return "Organization no Gender";
    }
}

interface Number {
    public String setNum(String number);

    String toString();
}

class NumberPerson implements Number, Serializable {

    String number;

    NumberPerson() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the number: ");
        this.number = ContactManager.getNumApi(); //Helper.getUserInput(); //in.nextLine();   !!
    }

    @Override
    public String setNum(String number) {
        return this.number = number;
    }

    @Override
    public String toString() {
        return number;  //Person number:
    }
}

class NumberOrganization implements Number, Serializable {
    String number;

    NumberOrganization() {
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter the number: ");
        this.number = ContactManager.getNumApi(); //this.number = Helper.getUserInput();   //in.nextLine();
    }

    @Override
    public String setNum(String number) {
        return this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}


class SerializationUtils {
    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
