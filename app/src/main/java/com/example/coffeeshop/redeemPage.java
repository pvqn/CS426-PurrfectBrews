package com.example.coffeeshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link redeemPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class redeemPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public redeemPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment redeemPage.
     */
    // TODO: Rename and change types and number of parameters
    public static redeemPage newInstance(String param1, String param2) {
        redeemPage fragment = new redeemPage();
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
        View rootView= inflater.inflate(R.layout.fragment_redeem_page, container, false);
        MaterialToolbar toolbar = rootView.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)requireActivity()).switchToFragmentGift();
            }
        });

        Button btn1=rootView.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score=((MainActivity)requireActivity()).getScore();
                if (score>=1500)
                {
                    ((MainActivity)requireActivity()).updateScore(1500, true);
                    Toast.makeText(requireContext(), "-1500 point. Your point: "+ String.valueOf(score-1500), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(requireContext(), "Your point is not enough to exchange.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn2=rootView.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score=((MainActivity)requireActivity()).getScore();
                if (score>=1000)
                {
                    ((MainActivity)requireActivity()).updateScore(1000, true);
                    Toast.makeText(requireContext(), "-1000 point. Your point: "+ String.valueOf(score-1000), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(requireContext(), "Your point is not enough to exchange.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn3=rootView.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score=((MainActivity)requireActivity()).getScore();
                if (score>=1200)
                {
                    ((MainActivity)requireActivity()).updateScore(1200, true);
                    Toast.makeText(requireContext(), "-1200 point. Your point: "+ String.valueOf(score-1200), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(requireContext(), "Your point is not enough to exchange.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}