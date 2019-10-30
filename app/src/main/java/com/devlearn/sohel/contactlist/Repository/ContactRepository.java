package com.devlearn.sohel.contactlist.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.devlearn.sohel.contactlist.Dao.ContactDao;
import com.devlearn.sohel.contactlist.Database.ContactDatabase;
import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ContactRepository {

    private ContactDao contactDao;
    private LiveData<List<Category>> allCategories;

    public ContactRepository(Application application)
    {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        allCategories = contactDao.getAllCategoryLive();
    }


    //livedata by default runs in background so no async is needed
    public LiveData<List<Category>> getAllCategoryLive(){
        return allCategories;
    }


    public List<Contact> getContactByCategoryId(int catId)
    {
        try {
            return new getAllContactsAsyncTask(contactDao).execute(catId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllCategory()
    {
        try {
            return new getAllCategoryAsyncTask(contactDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void insert(Contact contact){
        new InsertContactAsyncTast(contactDao).execute(contact);
    }

    public void insertCategory(Category category){
        new InsertCategoryAsyncTask(contactDao).execute(category);
    }

    public void update(Contact contact){
        new UpdateContactAsyncTast(contactDao).execute(contact);
    }

    public void delete(Contact contact){
        new DeleteContactAsyncTast(contactDao).execute(contact);
    }

    public void deleteAllContacts(){
        new DeleteAllContactAsyncTast(contactDao).execute();
    }

    //static so doesn't have a ref to repository class, so no memery leak
    //asynctask takes 3 peram, 1=param passed to asynch, 2=progress, 3=return result type we get back

    private static class getAllCategoryAsyncTask extends AsyncTask<Void,Void,List<Category>>{

        private ContactDao contactDao;

        private getAllCategoryAsyncTask(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return contactDao.getAllCategory();
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
        }
    }

    private static class getAllContactsAsyncTask extends AsyncTask<Integer,Void,List<Contact>>{

        private ContactDao contactDao;

        private getAllContactsAsyncTask(ContactDao contactDao){ this.contactDao = contactDao; }

        @Override
        protected List<Contact> doInBackground(Integer... integers) {
            return contactDao.getContactByCatId(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);
        }
    }



    private static class InsertContactAsyncTast extends AsyncTask<Contact,Void, Void>{

        private ContactDao contactDao;

        private InsertContactAsyncTast(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category,Void, Void>{

        private ContactDao contactDao;

        private InsertCategoryAsyncTask(ContactDao contactDao){ this.contactDao = contactDao; }

        @Override
        protected Void doInBackground(Category... categories) {
            contactDao.insertCategory(categories[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTast extends AsyncTask<Contact,Void, Void>{

        private ContactDao contactDao;

        private UpdateContactAsyncTast(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTast extends AsyncTask<Contact,Void, Void>{

        private ContactDao contactDao;

        private DeleteContactAsyncTast(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }
    }

    private static class DeleteAllContactAsyncTast extends AsyncTask<Contact,Void, Void>{

        private ContactDao contactDao;

        private DeleteAllContactAsyncTast(ContactDao contactDao){
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.deleteAllContacts();
            return null;
        }
    }
}
