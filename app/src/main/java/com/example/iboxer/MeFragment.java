package com.example.iboxer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        final TextView nickname = (TextView) rootView.findViewById(R.id.nickReal);
        final TextView count = (TextView) rootView.findViewById(R.id.CountryReal);
        final TextView ml = (TextView) rootView.findViewById(R.id.EmailReal);
        final TextView hsc = (TextView) rootView.findViewById(R.id.highScoreReal);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference();
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Users").child(uid).child("nickname").getValue(String.class);
                String email = dataSnapshot.child("Users").child(uid).child("email").getValue(String.class);
                String country = dataSnapshot.child("Users").child(uid).child("country").getValue(String.class);
                nickname.setText(name);
                count.setText(country);
                ml.setText(email);
                int highscore = 0;
                for(DataSnapshot ds: dataSnapshot.child("Scores").child(uid).getChildren())
                {
                    int sc = ds.child("score").getValue(Integer.class);
                    if(sc>highscore) highscore=sc;
                }
                hsc.setText(highscore + " pts");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button logout = (Button) rootView.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener(){
            Intent intent = new Intent(getActivity(), Login.class);
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                getActivity().finish();
            }
        });


        return rootView;
    }
}