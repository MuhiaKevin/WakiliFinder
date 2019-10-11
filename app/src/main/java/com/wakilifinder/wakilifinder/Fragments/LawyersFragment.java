package com.wakilifinder.wakilifinder.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Adapter.UserLawyerAdapter;
import com.wakilifinder.wakilifinder.Model.UserLawyer;

import com.wakilifinder.wakilifinder.R;

import java.util.ArrayList;
import java.util.List;

public class LawyersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<UserLawyer> mUsers;
    private UserLawyerAdapter userLawyerAdapter;
    private EditText search_users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lawyers, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();

        readLawyers();

        return view;
    }


    // read lawyers from database

    private void readLawyers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("Lawyers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserLawyer user = snapshot.getValue(UserLawyer.class);

                        assert user != null;
                        assert firebaseUser != null;

                        mUsers.add(user);

                    }

                    userLawyerAdapter = new UserLawyerAdapter(getContext(),mUsers);
                    recyclerView.setAdapter(userLawyerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
