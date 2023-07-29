package com.example.coffeeshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signUp.
     */
    // TODO: Rename and change types and number of parameters
    public static signUp newInstance(String param1, String param2) {
        signUp fragment = new signUp();
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
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextView signIn = rootView.findViewById(R.id.signIn);
        Button createAcc = rootView.findViewById(R.id.createAcc);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).switchToFragmentSignIn();
                //Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView name = rootView.findViewById(R.id.fullName);
                TextView email = rootView.findViewById(R.id.email);
                TextView address = rootView.findViewById(R.id.address);
                TextView password = rootView.findViewById(R.id.password);

                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        String userId = currentUser.getUid();
                                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                        usersRef.child(userId).child("name").setValue(name.getText().toString());
                                        usersRef.child(userId).child("address").setValue(address.getText().toString());
                                        usersRef.child(userId).child("phone").setValue(((MainActivity)requireActivity()).getPhone());

                                        ((MainActivity) requireActivity()).setName(name.getText().toString());
                                        ((MainActivity) requireActivity()).setAddress(address.getText().toString());
                                        ((MainActivity) requireActivity()).setEmail(email.getText().toString());

                                        ((MainActivity) requireActivity()).switchToFragmentShowingCoffee();
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
        return rootView;
    }
}