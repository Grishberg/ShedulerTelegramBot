package services;

import data.models.Contact;

import java.util.List;

/**
 * Created by grishberg on 29.09.16.
 */
public interface ContactService {
    void addContact(Contact contact);

    void removeContact(Integer id);

    boolean isExists(Integer id);

    List<Contact> getContacts();
}
