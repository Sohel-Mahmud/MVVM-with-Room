package com.devlearn.sohel.contactlist.ViewModel;

import android.app.Application;

import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.Repository.ContactRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private List<Contact> allContacts;
    private List<Category> allCategory;
    private LiveData<List<Category>> allCategoryLiveList;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allCategoryLiveList = repository.getAllCategoryLive();
    }

    //wrapper methods
    //as our activity can't directly access repository
    //it can only be accessed by ViewModel heheheheh....

    public void insertCategory(Category category) {repository.insertCategory(category);}

    public void insert(Contact contact){
        repository.insert(contact);
    }

    public void update(Contact contact){
        repository.update(contact);
    }


    public void delete(Contact contact){
        repository.delete(contact);
    }


    public void deleteAllContact(){
        repository.deleteAllContacts();
    }

    public List<Contact> getContactByCategoryId(int catId){
        allContacts = repository.getContactByCategoryId(catId);
        return allContacts;
    }

    public List<Category> getAllCategory(){
        allCategory = repository.getAllCategory();
        return allCategory;
    }

    public LiveData<List<Category>> getAllCategoryLiveList(){
        return allCategoryLiveList;
    }
}
