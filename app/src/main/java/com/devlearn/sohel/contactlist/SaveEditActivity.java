package com.devlearn.sohel.contactlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.ViewModel.ContactViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SaveEditActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PHONE = "PHONE";
    public static final String EXTRA_CAT_ID = "CAT_ID";
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_OBJ = "OBJ";
    public static final String EXTRA_EDIT_REQUEST = "EDIT";

    private AppCompatButton btnSave, btnDelete, btnAddCategory, btnUpdate;

    private ContactViewModel contactViewModelCat;

    private AppCompatSpinner spinner;

    private TextView txtBlue, txtGreen, txtRed, txtYellow;

    private String nameIntent, phoneIntent, colorIntent;
    private int id, catId=0;
    
    private String categoryName="";

    private TextInputEditText edtPhone,edtName;
    private String colorName;
    private ArrayList<String> categoryNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_edit);

        //init
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        spinner = findViewById(R.id.spinnerCategory);

        edtName = findViewById(R.id.name);
        edtPhone = findViewById(R.id.phone);

        contactViewModelCat = ViewModelProviders.of(this).get(ContactViewModel.class);

        populateSpinner();

        final Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle(Html.fromHtml("<font color='#4B7BFF'>"+"Edit Contact"+"</font>"));

            nameIntent = intent.getStringExtra(EXTRA_NAME);
            phoneIntent = intent.getStringExtra(EXTRA_PHONE);
            catId = intent.getIntExtra(EXTRA_CAT_ID,-1);
            id = intent.getIntExtra(EXTRA_ID,-1);

            edtName.setText(intent.getStringExtra(EXTRA_NAME));
            edtPhone.setText(intent.getStringExtra(EXTRA_PHONE));

            btnDelete.setVisibility(View.VISIBLE);


        }else{
            setTitle(Html.fromHtml("<font color='#4B7BFF'>"+"Add Contacts"+"</font>"));
        }
        //back btn in toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtPhone.getText()) || TextUtils.isEmpty(edtName.getText()))
                {
                    Toast.makeText(SaveEditActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

                }
                else if(categoryName.equals("") || catId==0)
                {
                    Toast.makeText(SaveEditActivity.this, "Please Add a category", Toast.LENGTH_SHORT).show();
                }
                else{
                    String name,phone;
                    name = edtName.getText().toString().trim();
                    phone = edtPhone.getText().toString().trim();
                    if(intent.hasExtra(EXTRA_ID))
                    {
                        updateContact(id,name, phone, categoryName,catId);

                    }else{
                        saveContact(name, phone, categoryName,catId);
                    }
                }
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCategoryDialog();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SaveEditActivity.this, MainActivity.class);

                Contact contact = new Contact(catId,nameIntent,phoneIntent);
                contact.setId(id);
                contactViewModelCat.delete(contact);
                Toast.makeText(SaveEditActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                finish();
            }
        });
    }

    private void updateContact(int id, String name, String phone, String categoryName, int catId) {
        Contact contact = new Contact(catId,name,phone);
        contact.setId(id);
        contactViewModelCat.update(contact);
        startActivity(new Intent(SaveEditActivity.this, MainActivity.class));
        finish();

    }

    private void populateSpinner() {

        contactViewModelCat.getAllCategoryLiveList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(final List<Category> categories) {
                if(categories!=null){
                    categoryNames = new ArrayList<String>();

                    for(int i = 0; i<categories.size(); i++){
                        categoryNames.add(categories.get(i).getCatName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SaveEditActivity.this, android.R.layout.simple_spinner_item,categoryNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            categoryName = categoryNames.get(i);
                            catId = categories.get(i).getCat_id();

                            Toast.makeText(SaveEditActivity.this, categoryName+" "+catId, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else{
                    Toast.makeText(SaveEditActivity.this, "No category Found! please add", Toast.LENGTH_SHORT).show();
                }
            }
        });
                

               


    }

    private void showAddCategoryDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add New Category");

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_category_add = inflater.inflate(R.layout.layout_dialog_category_add,null);

        //OTP code enter field
        final TextInputEditText edtCategoryName = layout_category_add.findViewById(R.id.edtCategoryName);

        dialog.setView(layout_category_add);

        dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(edtCategoryName.getText().toString())){
                    Toast.makeText(SaveEditActivity.this, "Please Enter Category!", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }else{
                    String catName = edtCategoryName.getText().toString();
                    createNewCategory(catName);
                    dialogInterface.dismiss();
                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void createNewCategory(String catName) {
        Category category = new Category(catName);
        contactViewModelCat.insertCategory(category);
        Toast.makeText(this, "Category Added!", Toast.LENGTH_SHORT).show();
    }

    private void saveContact(String name, String phone, String categoryName, int catId) {
        Intent intent = new Intent(SaveEditActivity.this,MainActivity.class);
        Contact contact = new Contact(catId, name, phone);
        contactViewModelCat.insert(contact);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SaveEditActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
