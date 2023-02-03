package project.contacts;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Methods {


    public static ArrayList<String> listOfcontacts;

    /*
    public static List<String> getCoList() throws IOException, ClassNotFoundException {
        ContactList cl = new ContactList();
        List<Contact> contactsList = cl.contactList;  // assign to list deserialized list

        return Methods.getList(contactsList, contactsList.size());
    }

     */
    public static List<Contact> remove(List<Contact> contactsList, int numOfrecords, int idxRemove) {
        //Scanner sc = new Scanner(System.in);
        if (numOfrecords == 0) {

            System.out.println("No records to remove!\n");
        } else {
            // get list
            int cnt = 1;
            for (Contact value : contactsList) {
                System.out.println(cnt + ". " + value.getTitle());
                cnt++;
            }
            System.out.print("Select a record: ");
            //int numToRem = sc.nextInt() - 1; // star to remove in list step by step
            int numToRem = idxRemove - 1; //idxRemove == 0 ? sc.nextInt() - 1 : idxRemove - 1; // -----> !!!!!!!

            contactsList.remove(numToRem);
            // method works with kostil 1 but after it changes disappears when api in cl works
            numOfrecords--;
            System.out.println("The record removed!\n");
            //sc.nextLine();
        }
        return contactsList;
    }

    public static List<String> getList(List<Contact> contactsList, int numOfrecords) {
        listOfcontacts = new ArrayList<>();
        if (numOfrecords == 0 && contactsList.isEmpty()) {
            System.out.println("No records\n");
        } else {
            int cnt = 1;
            for (Contact value : contactsList) {
                listOfcontacts.add(cnt + ". " + value.getTitle() + ", &nbsp [number:" + value.number + "]"); // собираем конакт лист
                System.out.println(cnt + ". " + value.getTitle() + ", &nbsp [number:" + value.number + "]");
                //System.out.println("listOfcontacts => "  + listOfcontacts);
                cnt++;
            }
        }
        return listOfcontacts;
    }

    public static List<Contact> edit(List<Contact> contactsList, int numOfrecords) {
        //Scanner in = new Scanner(System.in);
        if (numOfrecords == 0) {
            System.out.println("No records to edit!");
        } else {
            int cnt = 1;
            for (Contact value : contactsList) {
                System.out.println(cnt + ". " + value.getTitle());
                cnt++;
            }
            System.out.print("Select a record: ");
            int lineToEdit = Integer.parseInt(Helper.getUserInput()) - 1;   //in.nextInt() - 1; // line to edit
            if (contactsList.get(lineToEdit) instanceof Person) {     // edit Person
                System.out.print("Select a field (name, surname, birth, gender, number): ");
                String fieldName = Helper.getUserInput(); //in.next();
                if (fieldName.equals("name")) {  // edit name
                    ((Person) contactsList.get(lineToEdit)).updateName();
                }
                if (fieldName.equals("surname")) {  // edit surname
                    ((Person) contactsList.get(lineToEdit)).updateSurname();
                }
                if (fieldName.equals("birth")) {  // edit birth
                    ((Person) contactsList.get(lineToEdit)).updateBirth();
                }
                if (fieldName.equals("gender")) {  // edit gender
                    ((Person) contactsList.get(lineToEdit)).updateGender();
                }
                if (fieldName.equals("number")) {  // edit number
                    ((Person) contactsList.get(lineToEdit)).updateNumber();
                }

                LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
                ((Person) contactsList.get(lineToEdit)).timeUpd = now.toString();

            } else {
                System.out.print("Select a field (name, address, number): ");  // edit Organization
                String fieldName = Helper.getUserInput(); //in.next();
                if (fieldName.equals("name")) {
                    ((Organization) contactsList.get(lineToEdit)).updateName();  // edit number
                }
                if (fieldName.equals("number")) {
                    ((Organization) contactsList.get(lineToEdit)).updateNumber();  // edit number
                }
                if (fieldName.equals("address")) {
                    ((Organization) contactsList.get(lineToEdit)).updateAddress();   // edit address
                }
                LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
                ((Organization) contactsList.get(lineToEdit)).timeUpd = now.toString();
            }
            System.out.println("Saved");
            System.out.println();
            //in.nextLine();   // fix double sout        // 1 diabled
        }
        //in.close();
        return contactsList;
    }

    public static List<Contact> editInSearch(List<Contact> contactsList, int numOfrecords, int editedLine) {
        //Scanner in = new Scanner(System.in);
        if (numOfrecords == 0) {
            System.out.println("No records to edit!");
        } else {

            int lineToEdit = editedLine;// line to edit
            if (contactsList.get(lineToEdit) instanceof Person) {     // edit Person
                System.out.print("Select a field (name, surname, birth, gender, number): ");
                String fieldName = Helper.getUserInput();  //in.next();
                if (fieldName.equals("name")) {  // edit name
                    ((Person) contactsList.get(lineToEdit)).updateName();
                }
                if (fieldName.equals("surname")) {  // edit surname
                    ((Person) contactsList.get(lineToEdit)).updateSurname();
                }
                if (fieldName.equals("birth")) {  // edit birth
                    ((Person) contactsList.get(lineToEdit)).updateBirth();
                }
                if (fieldName.equals("gender")) {  // edit gender
                    ((Person) contactsList.get(lineToEdit)).updateGender();
                }
                if (fieldName.equals("number")) {  // edit number
                    ((Person) contactsList.get(lineToEdit)).updateNumber();
                }

                LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
                ((Person) contactsList.get(lineToEdit)).timeUpd = now.toString();

            } else {
                System.out.print("Select a field (name, address, number): ");  // edit Organization
                String fieldName = Helper.getUserInput(); //in.next();
                if (fieldName.equals("name")) {
                    ((Organization) contactsList.get(lineToEdit)).updateName();  // edit number
                }
                if (fieldName.equals("number")) {
                    ((Organization) contactsList.get(lineToEdit)).updateNumber();  // edit number
                }
                if (fieldName.equals("address")) {
                    ((Organization) contactsList.get(lineToEdit)).updateAddress();   // edit address
                }
                LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);  // set upd time
                ((Organization) contactsList.get(lineToEdit)).timeUpd = now.toString();
            }
            System.out.println("Saved");
        }
        //in.close();
        return contactsList;
    }

}
