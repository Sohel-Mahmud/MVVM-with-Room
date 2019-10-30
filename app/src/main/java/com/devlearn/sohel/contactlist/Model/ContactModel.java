package com.devlearn.sohel.contactlist.Model;

import com.devlearn.sohel.contactlist.Entity.Contact;

import java.util.List;

import androidx.room.Relation;

public class ContactModel {

    private int id;
    private String categoryName;
    private List<Contact> contacts = null;

    public ContactModel(String categoryName, List<Contact> contacts) {
        this.categoryName = categoryName;
        this.contacts = contacts;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
