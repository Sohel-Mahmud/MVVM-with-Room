package com.devlearn.sohel.contactlist.Dao;

import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Insert
    void insertCategory(Category category);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAllContacts();

    @Query("SELECT * FROM category_table ORDER BY catName ASC")
    public List<Category> getAllCategory();

    @Query("SELECT * FROM category_table ORDER BY catName ASC")
    public LiveData<List<Category>> getAllCategoryLive();

    @Query("SELECT * FROM contact_table WHERE category_id = :catId ORDER BY name ASC")
    public List<Contact> getContactByCatId(int catId);


}
