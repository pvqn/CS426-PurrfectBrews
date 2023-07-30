package com.example.coffeeshop;

import static java.lang.Math.round;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myCart extends Fragment implements coffeeCartRecycleAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    coffeeCartRecycleAdapter mAdapter;
    CoordinatorLayout coordinatorLayout;
    ArrayList<CoffeeCart> coffeeCarts;
    TextInputEditText editText;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView totalPrice;
    String code="1";

    public myCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myCart.
     */
    // TODO: Rename and change types and number of parameters
    public static myCart newInstance(String param1, String param2) {
        myCart fragment = new myCart();
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
        View rootView = inflater.inflate(R.layout.fragment_my_cart, container, false);
        MaterialToolbar toolbar = rootView.findViewById(R.id.topAppBar);
        totalPrice = rootView.findViewById(R.id.totalPrice);
        editText = rootView.findViewById(R.id.code);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // This method is called when the text is being changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has been changed
                String userInput = editable.toString();
                Log.d("ok code", "oke");
                Log.d("ok code", userInput);
                double total = 0;
                for (int i = 0; i < coffeeCarts.size(); ++i) {
                    if (coffeeCarts.get(i).isSelected())
                        total += coffeeCarts.get(i).getPrice();
                }
                if (userInput.equals("CS426")) {
                    Log.d("ok code", String.valueOf(((MainActivity)requireActivity()).getNewMember()));

                    if (((MainActivity)requireActivity()).getNewMember() == 1)
                    {

                        totalPrice.setText("TOTAL: $ " + String.valueOf(round(total*0.95)));
                        code=userInput;

                    }
                    else
                    {
                        Toast.makeText(requireContext(), "Invalid code.", Toast.LENGTH_SHORT).show();

                    }
                } else {totalPrice.setText("TOTAL: $ " + String.valueOf(total)); code="1";}
                // Do something with the user input after they finish typing
            }
        });
        Button checkout = rootView.findViewById(R.id.checkOut);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = DateTimeUtils.getCurrentDate(); // Format: dd/MM/yyyy
                String currentTime = DateTimeUtils.getCurrentTime();
                ArrayList<CoffeeCart> temp = new ArrayList<>();
                ArrayList<CoffeeCart> tempOrdered = new ArrayList<>();

                for (int i = 0; i < mAdapter.getData().size(); ++i) {
                    if (mAdapter.getData().get(i).isSelected()) {
                        mAdapter.getData().get(i).setSelected(false);
                        ((MainActivity)requireActivity()).updateScore((int)round(mAdapter.getData().get(i).getPrice()*10), false);
                        mAdapter.getData().get(i).setDateTime(currentDate + " " + currentTime);
                        tempOrdered.add((mAdapter.getData().get(i)));

                    } else {
                        temp.add(mAdapter.getData().get(i));
                    }
                }


                double total = 0;
                for (int i = 0; i < tempOrdered.size(); ++i) {
                    total += tempOrdered.get(i).getPrice();
                }

                if (total > 0) {

                    if (code.equals("CS426")) {
                        if (((MainActivity)requireActivity()).getNewMember() == 1)
                        {
                            total= round(total*0.95);
                        }
                    }
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                        usersRef.child(userId).child("newMember").setValue(0);
                        }
                    ((MainActivity)requireActivity()).setNewMember(0);
                        ((MainActivity) requireActivity()).removeAllCoffeeCart();
                    ((MainActivity) requireActivity()).setCoffeeCarts(temp);
                    BillItem tempBill = new BillItem(currentDate + " " + currentTime, ((MainActivity) requireActivity()).getAddress(), total,
                            tempOrdered);

                    //Log.d("tempBill",  " coffeeCarts count: " + tempBill.getCoffeeCarts().size());

                    ((MainActivity) requireActivity()).addBillItems(tempBill);
                    int t=0;
                    for (int i=0; i<tempOrdered.size();++i)
                    {
                        t+=tempOrdered.get(i).getQuantity();
                    }
                    ((MainActivity)requireActivity()).updateCardProgress(t, false);

                    ((MainActivity) requireActivity()).switchToFragmentOrderedSuccess();
                } else {
                    Toast.makeText(requireContext(), "You must select at least one time to check out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) requireActivity()).switchToFragmentShowingCoffee();
            }
        });

        recyclerView = rootView.findViewById(R.id.recyclerView);
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        coffeeCarts = ((MainActivity) requireActivity()).getCoffeeCarts();
        mAdapter = new coffeeCartRecycleAdapter(coffeeCarts);
        mAdapter.setOnItemClickListener(this);
        double total = 0;
        for (int i = 0; i < mAdapter.getData().size(); ++i) {
            if (mAdapter.getData().get(i).isSelected())
                total += mAdapter.getData().get(i).getPrice();
        }
        totalPrice.setText("TOTAL: $ " + String.valueOf(total));
        recyclerView.setAdapter(mAdapter);
        enableSwipeToDeleteAndUndo();
        return rootView;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final CoffeeCart item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                        double total = 0;
                        if (mAdapter.getData().size() > 0)
                            for (int j = 0; j < mAdapter.getData().size(); ++j) {
                                if (mAdapter.getData().get(j).isSelected())
                                    total += mAdapter.getData().get(j).getPrice();
                            }
                        totalPrice.setText("TOTAL: $ " + String.valueOf(total));

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
                double total = 0;
                if (mAdapter.getData().size() > 0)
                    for (int j = 0; j < mAdapter.getData().size(); ++j) {
                        if (mAdapter.getData().get(j).isSelected())
                            total += mAdapter.getData().get(j).getPrice();
                    }
                totalPrice.setText("TOTAL: $ " + String.valueOf(total));
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(CoffeeCart coffeeCart) {
        double total = 0;
        for (int i = 0; i < coffeeCarts.size(); ++i) {
            if (coffeeCarts.get(i).isSelected())
                total += coffeeCarts.get(i).getPrice();
        }


        Log.d("ok code", code);

        if (code.equals("CS426")) {
            if (((MainActivity)requireActivity()).getNewMember() == 1)
            {

                totalPrice.setText("TOTAL: $ " + String.valueOf(round(total*0.95)));


            }
            else
            {
                Toast.makeText(requireContext(), "Invalid code.", Toast.LENGTH_SHORT).show();
            }
        }
        else {totalPrice.setText("TOTAL: $ " + String.valueOf(total));code="1";}
    }

}