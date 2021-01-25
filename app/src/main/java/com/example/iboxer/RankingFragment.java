package com.example.iboxer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        final ListView list = (ListView) rootView.findViewById(R.id.list);
        DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference();
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Test","Im here");
                ArrayList<RowData> rowlist = new ArrayList<RowData>();
                for(DataSnapshot ds: dataSnapshot.child("Scores").getChildren())
                {
                    for(DataSnapshot ds2: ds.getChildren())
                    {
                        int points = ds2.child("score").getValue(Integer.class);
                        String userId = ds2.child("userId").getValue(String.class);
                        rowlist.add(new RowData("","",points,userId));
                    }
                }
                for(int i=0; i<rowlist.size(); i++)
                {

                    rowlist.get(i).setNickname(dataSnapshot.child("Users").child(rowlist.get(i).getUserId()).child("nickname").getValue(String.class));
                    rowlist.get(i).setCountry(dataSnapshot.child("Users").child(rowlist.get(i).getUserId()).child("country").getValue(String.class));
                    Log.i("Test","User: " + rowlist.get(i).getNickname() + " Points: " + rowlist.get(i).getPoints());
                }
                Collections.sort(rowlist);
                ListAdapter adapter = new ListAdapter(rootView.getContext(),R.layout.list_row,rowlist);
                list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return rootView;
    }
}