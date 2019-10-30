package com.devlearn.sohel.contactlist.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devlearn.sohel.contactlist.Entity.Category;
import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.MainActivity;
import com.devlearn.sohel.contactlist.Model.ContactModel;
import com.devlearn.sohel.contactlist.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactListCatAdapter extends RecyclerView.Adapter<ContactListCatAdapter.ContactCatViewHolder> {

    private List<ContactModel> contactModelsList = new ArrayList<>();
    private Context context;

    public ContactListCatAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_contact_list, parent, false);

        return new ContactCatViewHolder(itemView);
    }

    public void setContactModelsList(List<ContactModel> contactModelsList) {
        this.contactModelsList = contactModelsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactCatViewHolder holder, int position) {

        List<Contact> contact = contactModelsList.get(position).getContacts();

            holder.txtCategoryName.setText(contactModelsList.get(position).getCategoryName());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 4);
            holder.contactListView.setLayoutManager(mLayoutManager);
            holder.contactListView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(4, dpToPx(5), true));
            holder.contactListView.setItemAnimator(new DefaultItemAnimator());
            holder.contactListView.setHasFixedSize(true);

            final ContactCardAdapter adapter = new ContactCardAdapter(context);
            holder.contactListView.setAdapter(adapter);
            adapter.setContactList(contactModelsList.get(position).getContacts());




//        adapter.setOnitemLongClickListener(new ContactCardAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(Contact contact) {
//                Intent intent = new Intent(MainActivity.this, SaveEditActivity.class);
//                intent.putExtra(SaveEditActivity.EXTRA_ID, contact.getId());
//                intent.putExtra(SaveEditActivity.EXTRA_NAME, contact.getName());
//                intent.putExtra(SaveEditActivity.EXTRA_PHONE, contact.getPhone());
//                startActivityForResult(intent, EDIT_CONTACT_REQUEST);
//            }
//        });


    }

    private int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public int getItemCount() {
        return contactModelsList.size();
    }


    public static class ContactCatViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView txtCategoryName;
        private RecyclerView contactListView;

        public ContactCatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCatName);
            contactListView = itemView.findViewById(R.id.contact_list_view);
        }
    }

}
