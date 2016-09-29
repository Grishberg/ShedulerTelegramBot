package services;

import data.models.Contact;

import java.util.List;

/**
 * Created by grishberg on 29.09.16.
 */
public class ContactServiceImpl implements ContactService {
    private final DbService dbService;

    public ContactServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void addContact(Contact contact) {

    }

    @Override
    public void removeContact(Contact contact) {

    }

    @Override
    public List<Contact> getContacts() {
        return null;
    }
}
