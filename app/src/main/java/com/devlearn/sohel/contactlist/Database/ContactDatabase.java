package com.devlearn.sohel.contactlist.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.devlearn.sohel.contactlist.Dao.ContactDao;
import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class, Category.class}, version = 1,exportSchema = false)
public abstract class ContactDatabase extends RoomDatabase {

    //singletoon class, can't create multiple instance of it
    private static ContactDatabase instance;

    //is used to access our dao, room will handle everything
    public abstract ContactDao contactDao();

    //singleton database
    //use only one thread at a time

    public static synchronized ContactDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class,"contact_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
//
//        private ContactDao contactDao;
//
//        private PopulateDbAsyncTask(ContactDatabase db){
//            contactDao = db.contactDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            contactDao.insert(new Contact("Sohel","01816234343","red"));
//            contactDao.insert(new Contact("Sohel 3","01816234342","red"));
//            contactDao.insert(new Contact("Sohel_Mahmud","01816234341","red"));
//            contactDao.insert(new Contact("Sohel Mahmud","01816234343","red"));
//            contactDao.insert(new Contact("Sohel 45","01816234343","red"));
//            contactDao.insert(new Contact("Sohel Mahmud Avon","01816234343","red"));
//
//            return null;
//        }
//    }
}
