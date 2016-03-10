package com.example.eigenaar.booking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class SignInFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static int USER_ACCOUNT_ID_POSITION = 1;

    private EditText editID, editPassword;
    private String ID, password;
    private Button loginButton;

    private DataSource datasource;

   // private AccountHolder accountHolder;

    public SignInFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SignInFragment newInstance(int sectionNumber) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_activity_login, container, false);

        editID = (EditText) rootView.findViewById(R.id.id);
        editPassword = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.button_log_in);

        datasource = new DataSource(this.getContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = editID.getText().toString();
                password = editPassword.getText().toString();

                boolean isEmpty = datasource.checkUserTable();

                if (!isEmpty)
                {
                    Cursor cursor;

                    long lastId = datasource.checkAccountRowId();

                    AccountHolder accountHolder = datasource.getUser(lastId);

                    Log.i("TESTING", " accountHolderID " + accountHolder.getUserID());
                    Log.i("TESTING", " accountHolderPASSWORD " + accountHolder.getUserPassword());

                    if (ID.equals(accountHolder.getUserID())
                            && password.equals(accountHolder.getUserPassword()))
                    {
                        SaveSharedPreference.setUserName(getContext(), ID);

                        Toast.makeText(getContext(), "Entered", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                        getActivity().finish();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Wrong ID and password combination", Toast.LENGTH_LONG).show();
                        editID.setText("");
                        editPassword.setText("");
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No account yet", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
