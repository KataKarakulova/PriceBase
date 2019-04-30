package com.example.gearquicker.pricebase;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class InputFrame extends Fragment {

    private AutoCompleteTextView item;
    private EditText price;
    private FloatingActionButton fab;

    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.input_fragment, container, false);
        setHasOptionsMenu(true);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {
        item = view.findViewById(R.id.tfItem);
        price = view.findViewById(R.id.tfPrice);
        fab = view.findViewById(R.id.fab);

        List<OrmItem> items = realm.copyFromRealm(realm.where(OrmItem.class).findAll());
        item.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCompletesData()) {
                    saveOperation();
                    Toast.makeText(getContext(), getString(R.string.toast_saved), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.toast_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveOperation() {
        realm.beginTransaction();
        OrmItem ormItem = new OrmItem(item.getText().toString());
        OrmPrice ormPrice = new OrmPrice(UUID.randomUUID().toString(), ormItem, System.currentTimeMillis(), Double.valueOf(price.getText().toString()));
        realm.insertOrUpdate(ormItem);
        realm.insertOrUpdate(ormPrice);

        List<OrmItem> items = realm.copyFromRealm(realm.where(OrmItem.class).findAll());
        item.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));
        realm.commitTransaction();
    }

    private boolean checkCompletesData() {
        return !(item.getText().toString().equals("") || price.getText().toString().equals(""));
    }

}
