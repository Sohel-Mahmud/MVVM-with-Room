package com.devlearn.sohel.contactlist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import com.devlearn.sohel.contactlist.Adapter.ContactCardAdapter;
import com.devlearn.sohel.contactlist.Adapter.ContactListCatAdapter;
import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.Model.ContactModel;
import com.devlearn.sohel.contactlist.ViewModel.ContactViewModel;
import com.devlearn.sohel.contactlist.ViewModel.ContactViewModel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CONTACT_REQUEST = 1;
    public static final int EDIT_CONTACT_REQUEST = 2;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 22;

    private ContactViewModel contactViewModel;
    private ContactViewModel2 contactViewModel2, contactViewModelGreen, contactViewModelYellow;

    private RelativeLayout redCat, blueCat, greenCat, yellowCat;
    private int[] intArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SaveEditActivity.class);
                startActivity(intent);
                finish();
            }
        });
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel2 = ViewModelProviders.of(this).get(ContactViewModel2.class);
        //contactGrid
        RecyclerView contactListRecycler = findViewById(R.id.contactListRecycler);

        contactListRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        contactListRecycler.setItemAnimator(new DefaultItemAnimator());
        contactListRecycler.setHasFixedSize(true);

        final ContactListCatAdapter adapter = new ContactListCatAdapter(getBaseContext());
        contactListRecycler.setAdapter(adapter);
        //List<Category> categoriesList = contactViewModel.getAllCategory();

        contactViewModel.getAllCategoryLiveList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories!=null) {
                    final List<ContactModel> contactModelsList = new ArrayList<>();
                    for (int i = 0; i < categories.size(); i++) {
                        int catId = categories.get(i).getCat_id();
                        List<Contact> contactList = contactViewModel.getContactByCategoryId(catId);
                        ContactModel contactModel = new ContactModel(categories.get(i).getCatName(), contactList);
                        contactModelsList.add(contactModel);

                    }
                    adapter.setContactModelsList(contactModelsList);

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
