package com.ritvi.kaajneeti.fragment.myconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.ritvi.kaajneeti.adapter.ContactsAdapter;
import com.ritvi.kaajneeti.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 04-03-2018.
 */

public class ContactFragment extends Fragment{
    @BindView(R.id.rv_users)
    RecyclerView rv_users;
    List<Contact> contacts = Contacts.getQuery().find();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_search_voter,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
    }
    ContactsAdapter searchVoterAdapter;
    public void attachAdapter(){
        searchVoterAdapter= new ContactsAdapter(getActivity(), this, contacts);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(searchVoterAdapter);
        rv_users.setLayoutManager(linearLayoutManager);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }
}
