package com.example.coffeeshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderedSucess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderedSucess extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderedSucess() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderedSucess.
     */
    // TODO: Rename and change types and number of parameters
    public static orderedSucess newInstance(String param1, String param2) {
        orderedSucess fragment = new orderedSucess();
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
        View rootView= inflater.inflate(R.layout.fragment_ordered_sucess, container, false);
        KonfettiView konfettiView=rootView.findViewById(R.id.konfettiView);
        List<Size> size= new ArrayList<Size>();
        size.add(Size.Companion.getSMALL());
        size.add(Size.Companion.getLARGE());
        size.add(Size.Companion.getMEDIUM());

        List<Integer> Color= new ArrayList<Integer>();
        Color.add(0xfce18a);
        Color.add(0xff726d);
        Color.add(0xf4306d);
        Color.add(0xb48def);

        List<Shape> shape=new ArrayList<Shape>();
        shape.add(Shape.Square.INSTANCE);
        shape.add(Shape.Circle.INSTANCE);

        Position position= new Position.Relative(0.5,0.3);
        Rotation rotation=new Rotation();
        rotation.getMultiplier2D();

        EmitterConfig emitter= new Emitter(100, TimeUnit.MILLISECONDS).max(100);

        konfettiView.start(new Party(0,360,30,0,0.9f, size, Color, shape,2000,true, position,0, rotation, emitter));

        return rootView;
    }
}