package com.devlearn.sohel.contactlist.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devlearn.sohel.contactlist.Entity.Contact;
import com.devlearn.sohel.contactlist.MainActivity;
import com.devlearn.sohel.contactlist.R;
import com.devlearn.sohel.contactlist.SaveEditActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ContactCardAdapter extends RecyclerView.Adapter<ContactCardAdapter.ContactViewHolder> {

    private List<Contact> contactList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private Context context;


    public ContactCardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_card, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        if(contact!=null)
        {
            String name = contact.getName();
            holder.txtName.setText(name);
            holder.txtThumbnail.setText(name.substring(0, 1).toUpperCase());
            holder.txtPhoneNumber.setText(contact.getPhone());

        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtThumbnail, txtPhoneNumber;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtThumbnail = itemView.findViewById(R.id.thumbnail);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Contact contact = contactList.get(position);
                        final String phoneNumber = contact.getPhone();

                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:" + phoneNumber));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Contact contact = contactList.get(position);
                        Intent intent = new Intent(context, SaveEditActivity.class);
                        intent.putExtra(SaveEditActivity.EXTRA_ID, contact.getId());
                        intent.putExtra(SaveEditActivity.EXTRA_NAME, contact.getName());
                        intent.putExtra(SaveEditActivity.EXTRA_PHONE, contact.getPhone());
                        intent.putExtra(SaveEditActivity.EXTRA_CAT_ID,contact.getCategory_id());
                        intent.putExtra(SaveEditActivity.EXTRA_EDIT_REQUEST,MainActivity.EDIT_CONTACT_REQUEST);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Contact contact);
    }

    public void setOnitemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
