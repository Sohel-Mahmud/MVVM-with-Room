package com.devlearn.sohel.contactlist.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table", indices = {@Index("cat_id")})
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cat_id")
    private int cat_id;

    private String catName;

    public Category(String catName) {
        this.catName = catName;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCatName() {
        return catName;
    }
}
