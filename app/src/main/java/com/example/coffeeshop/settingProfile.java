package com.example.coffeeshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yalantis.ucrop.UCrop;

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
    private static final int REQUEST_IMAGE_GALLERY = 1;
    private ImageView avatarImageView;
    private Uri selectedImageUri;

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
        TextInputEditText fullName = rootView.findViewById(R.id.fullName);
        TextInputEditText email = rootView.findViewById(R.id.email);
        TextInputEditText address = rootView.findViewById(R.id.address);
        TextInputEditText phoneNumber = rootView.findViewById(R.id.phoneNumb);

        Button signOut = rootView.findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                    usersRef.child(userId).child("name").setValue(fullName.getText().toString());
                    usersRef.child(userId).child("address").setValue(address.getText().toString());
                    usersRef.child(userId).child("phone").setValue(phoneNumber.getText().toString());

                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                ((MainActivity) requireActivity()).switchToFragmentSignIn();
            }
        });

        fullName.setText(((MainActivity) requireActivity()).getName());
        email.setText(((MainActivity) requireActivity()).getEmail());
        address.setText(((MainActivity) requireActivity()).getAddress());
        phoneNumber.setText(((MainActivity) requireActivity()).getPhone());

        ImageView test = rootView.findViewById(R.id.editName);
        ImageView test1 = rootView.findViewById(R.id.editEmail);
        ImageView test2 = rootView.findViewById(R.id.editPhoneNumb);
        ImageView test3 = rootView.findViewById(R.id.editAddress);

        avatarImageView = rootView.findViewById(R.id.profile);
        Bitmap updatedBitmap = ((MainActivity) requireActivity()).getAvatarBitmap();

        if (updatedBitmap != null) {
            // Set the updated bitmap to the avatar ImageView
            avatarImageView.setImageBitmap(updatedBitmap);
        }
        Button editAvatar = rootView.findViewById(R.id.editAvatar);
        editAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageGallery();
            }
        });
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

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                    usersRef.child(userId).child("name").setValue(fullName.getText().toString());
                    usersRef.child(userId).child("address").setValue(address.getText().toString());
                    usersRef.child(userId).child("phone").setValue(phoneNumber.getText().toString());

                }
                ((MainActivity) requireActivity()).setPhone(phoneNumber.getText().toString());
                ((MainActivity) requireActivity()).setName(fullName.getText().toString());
                ((MainActivity) requireActivity()).setEmail(email.getText().toString());
                ((MainActivity) requireActivity()).setAddress(address.getText().toString());

                ((MainActivity) requireActivity()).switchToFragmentShowingCoffee();
            }
        });

        return rootView;
    }

    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Start the UCrop activity for image cropping
                    startCropActivity(selectedImageUri);
                }
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            // Handle the result from UCrop
            handleCropResult(data);
        }
    }

    private void startCropActivity(Uri sourceUri) {
        // Specify the destination URI for the cropped image
        Uri destinationUri = Uri.fromFile(new File(requireActivity().getCacheDir(), "cropped_image.jpg"));

        // Start UCrop activity with the selected image URI and the destination URI
        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1, 1) // Set the aspect ratio to 1:1 for square cropping
                .start(requireContext(), this);
    }

    private void handleCropResult(Intent result) {
        // Get the cropped image URI from the UCrop result
        final Uri resultUri = UCrop.getOutput(result);

        if (resultUri != null) {
            try {
                // Get the bitmap from the cropped image URI
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), resultUri);

                // Set the cropped bitmap to the avatar ImageView
                avatarImageView.setImageBitmap(bitmap);
                ((MainActivity) requireActivity()).setAvatarBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}