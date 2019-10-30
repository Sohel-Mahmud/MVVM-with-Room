package com.devlearn.sohel.contactlist.ViewModel;

import android.app.Application;

import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.Repository.ContactRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ContactViewModel2 extends AndroidViewModel {

    private ContactRepository repository;
    private List<Contact> allContacts;

    public ContactViewModel2(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
    }

    public List<Contact> getContactByCategoryId(int catId){
        allContacts = repository.getContactByCategoryId(catId);
        return allContacts;
    }
}
