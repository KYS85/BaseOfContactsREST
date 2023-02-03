package project.contacts;

import java.io.IOException;
import java.util.List;

public interface ContactsInterface {

    //String inputCollector(String key);

    List<String> getContactList() throws IOException, ClassNotFoundException;

    String getContactInfo(int idx) throws IOException, ClassNotFoundException;

    List<Contact> removeContact(int idxToDel) throws IOException, ClassNotFoundException;

    /*
    List<Contact> addPerson(String nameApi, String surnameApi, String birthApi, String genderApi) throws IOException, ClassNotFoundException;
    */
    List<Contact> addPerson();

    List<Contact> addOrg();

    List<Contact> updNameApi(int idx, String name);

    List<Contact> updSurnameOrAddrApi(int idx, String name);

    List<Contact> updNumberApi(int idx, String number);

    List<Contact> updGenderApi(int idx, String gender);

    List<Contact> updBirthApi(int idx, String birth);


}
