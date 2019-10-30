package com.devlearn.sohel.contactlist.Entity;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table",foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "cat_id",
        childColumns = "category_id",
        onDelete = ForeignKey.NO_ACTION))

public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String phone;

    @ColumnInfo(name = "category_id")
    private int category_id;
    public Contact(int category_id, String name, String phone) {
        this.category_id = category_id;
        this.name = name;
        this.phone = phone;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    //only one setter for setting id
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

}
