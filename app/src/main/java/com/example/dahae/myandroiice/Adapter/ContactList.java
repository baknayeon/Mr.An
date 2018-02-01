package com.example.dahae.myandroiice.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.TriggerForPhoneReception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b_newyork on 2016-01-21.
 */
public class ContactList extends Activity {
    private ListView lv_contactlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_list);
        lv_contactlist = (ListView) findViewById(R.id.planList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ContactsAdapter adapter = new ContactsAdapter(getApplicationContext(),R.layout.litem_phonelist
                , getContactList());

        lv_contactlist.setAdapter(adapter);
        lv_contactlist
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> contactlist, View v,
                                            int position, long resid) {
                        Contact phonenumber = (Contact) contactlist
                                .getItemAtPosition(position);

                        if (phonenumber == null) {
                            return;
                        }

                        Intent data = new Intent();
                        data.putExtra(TriggerForPhoneReception.SELECTED_PHONE, phonenumber
                                .getPhonenum().replaceAll("-", ""));
                        data.putExtra(TriggerForPhoneReception.SELECTED_NAME, phonenumber
                                .getName());
                        setResult(TriggerForPhoneReception.SUCCESS, data);
                        finish();
                    }
                });

    }

    /**
     * 연락처를 가져오는 메소드.
     *
     * @return
     */
    private ArrayList<Contact> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = managedQuery(uri, projection, null,
                selectionArgs, sortOrder);

        ArrayList<Contact> contactlist = new ArrayList<Contact>();

        if (contactCursor.moveToFirst()) {
            do {
                String phonenumber = contactCursor.getString(1).replaceAll("-",
                        "");
                if (phonenumber.length() == 10) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 6) + "-"
                            + phonenumber.substring(6);
                } else if (phonenumber.length() > 8) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 7) + "-"
                            + phonenumber.substring(7);
                }

                Contact acontact = new Contact();
                acontact.setPhotoid(contactCursor.getLong(0));
                acontact.setPhonenum(phonenumber);
                acontact.setName(contactCursor.getString(2));

                contactlist.add(acontact);
            } while (contactCursor.moveToNext());
        }

        return contactlist;

    }

    private class ContactsAdapter extends ArrayAdapter<Contact> {

        private int resId;
        private ArrayList<Contact> contactlist;
        private LayoutInflater Inflater;

        public ContactsAdapter(Context context, int textViewResourceId,
                               List<Contact> objects) {
            super(context, textViewResourceId, objects);
            resId = textViewResourceId;
            contactlist = (ArrayList<Contact>) objects;
            Inflater = (LayoutInflater) (context)
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public View getView(int position, View v, ViewGroup parent) {
            ViewHolder holder;
            if (v == null) {
                v = Inflater.inflate(resId, null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) v.findViewById(R.id.tv_name);
                holder.tv_phonenumber = (TextView) v
                        .findViewById(R.id.tv_phonenumber);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            Contact acontact = contactlist.get(position);

            if (acontact != null) {
                holder.tv_name.setText(acontact.getName());
                holder.tv_phonenumber.setText(acontact.getPhonenum());

            }
            return v;
        }

        private class ViewHolder {
            TextView tv_name;
            TextView tv_phonenumber;
        }

    }
}
