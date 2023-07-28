package com.example.coffeeshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settingProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingProfile extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public settingProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settingProfile.
     */
    public static settingProfile newInstance(String param1, String param2) {
        settingProfile fragment = new settingProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting_profile, container, false);
        TextInputEditText fullName=rootView.findViewById(R.id.fullName);
        TextInputEditText email=rootView.findViewById(R.id.email);
        TextInputEditText address=rootView.findViewById(R.id.address);
        TextInputEditText phoneNumber=rootView.findViewById(R.id.phoneNumb);

        fullName.setText(((MainActivity)requireActivity()).getName());
        email.setText(((MainActivity)requireActivity()).getEmail());
        address.setText(((MainActivity)requireActivity()).getAddress());
        phoneNumber.setText(((MainActivity)requireActivity()).getPhone());

        ImageView test=rootView.findViewById(R.id.editName);
        ImageView test1=rootView.findViewById(R.id.editEmail);

        ImageView test2=rootView.findViewById(R.id.editPhoneNumb);

        ImageView test3=rootView.findViewById(R.id.editAddress);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName.setEnabled(true);
            }
        });
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setEnabled(true);
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber.setEnabled(true);
            }
        });
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address.setEnabled(true);
            }
        });
        MaterialToolbar toolbar = rootView.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)requireActivity()).setPhone(phoneNumber.getText().toString());
                ((MainActivity)requireActivity()).setName(fullName.getText().toString());
                ((MainActivity)requireActivity()).setEmail(email.getText().toString());
                ((MainActivity)requireActivity()).setAddress(address.getText().toString());

                ((MainActivity)requireActivity()).switchToFragmentShowingCoffee();
            }
        });

        return rootView;
    }
}