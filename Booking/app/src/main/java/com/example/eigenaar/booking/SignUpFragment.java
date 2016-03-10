package com.example.eigenaar.booking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Eigenaar on 9-3-2016.
 */
public class SignUpFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private EditText editID, editPassword, editConfirmPassword;
    private String ID, password, confirmPassword;
    private Button createButton, deleteButton;

    private DataSource datasource;

    public SignUpFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_activity_signup, container, false);

        editID = (EditText) rootView.findViewById(R.id.id);
        editPassword = (EditText) rootView.findViewById(R.id.password);
        editConfirmPassword = (EditText) rootView.findViewById(R.id.confirm_password);
        createButton = (Button) rootView.findViewById(R.id.create_button);
        deleteButton = (Button) rootView.findViewById(R.id.delete_button);

        datasource = new DataSource(this.getContext());

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmpty = datasource.checkUserTable();

                ID = editID.getText().toString();
                password = editPassword.getText().toString();
                confirmPassword = editConfirmPassword.getText().toString();

                if(!(password.equals(confirmPassword)))
                {
                    Toast.makeText(getContext(), "Passwords are not matching", Toast.LENGTH_LONG).show();

                    //Show field hint again
                    editID.setText("");
                    editPassword.setText("");
                    editConfirmPassword.setText("");
                }
                else if(!isEmpty)
                {
                    Toast.makeText(getContext(), "You already have an account", Toast.LENGTH_LONG).show();

                    //Show field hint again
                    editID.setText("");
                    editPassword.setText("");
                    editConfirmPassword.setText("");
                }
                else
                {
                    long accountId = datasource.createAccount(ID, password, confirmPassword);
                    Toast.makeText(getContext(), "Account created", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isEmpty = datasource.checkUserTable();

                if (!isEmpty)
                {

                    long lastId = datasource.checkAccountRowId();
                    AccountHolder accountHolder = datasource.getUser(lastId);
                    datasource.deleteAccount(accountHolder);
                    Toast.makeText(getContext(),"Deleted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"No account yet", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
