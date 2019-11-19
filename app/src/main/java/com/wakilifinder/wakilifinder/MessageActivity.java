package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Adapter.MessageAdapter;
import com.wakilifinder.wakilifinder.Fragments.ExampleDialog;
import com.wakilifinder.wakilifinder.Model.Chat;
import com.wakilifinder.wakilifinder.Model.UserLawyer;
import com.wakilifinder.wakilifinder.Model.Userclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    String userid;

    RecyclerView recyclerView;

    FirebaseUser fuser;
    DatabaseReference reference;

    ValueEventListener seenListener;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userloggedin = intent.getStringExtra("user");

                if (userloggedin.equals("client")){
                    startActivity(new Intent(MessageActivity.this, HomeClient.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else if (userloggedin.equals("lawyer")){
                    startActivity(new Intent(MessageActivity.this, HomeLawyer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);



        userid = intent.getStringExtra("userid");
        String user = intent.getStringExtra("user");

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        // button to send message

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();

                // if messge not empty then send messge
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(), userid, msg);
                    text_send.setText("");
                }

                else{
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                    text_send.setText("");
                }
            }
        });



        if (user.equals("client")){
            reference = FirebaseDatabase.getInstance().getReference("Users").child("Lawyers").child(userid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserLawyer user =  dataSnapshot.getValue(UserLawyer.class);
                    username.setText(user.getUsername());

                    if (user.getImageurl() == null){
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(getApplicationContext()).load(user.getImageurl()).into(profile_image);
                    }

                    readMessages(fuser.getUid(), userid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        else if (user.equals("lawyer")){

            reference = FirebaseDatabase.getInstance().getReference("Users").child("Clients").child(userid);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Userclient user = dataSnapshot.getValue(Userclient.class);

                    username.setText(user.getUsername());
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                    readMessages(fuser.getUid(), userid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




        seenMessage(userid);

    }

    private void seenMessage(final String userid){

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    // format message and save in database and add user to chat fragment

    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist").child(receiver).child(sender);

        chatRefReceiver.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRefReceiver.child("id").setValue(fuser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    // set menu layout on the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        intent = getIntent();
        String user = intent.getStringExtra("user");

        // if not logged in as lawyer then do not show the menu otherwise show
        if (user.equals("client")){
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }
        else {
            return false;
        }
    }



    // if button pay lawyer is clicked then open activity for client to pay lawyer

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // pay lawyer button
            case  R.id.pay:
                Intent i = new Intent(getApplicationContext(),ClientPay.class);
                startActivity(i);
                return true;

            // pop up to let client leave a lawyers review lawyer button
            case  R.id.reviewlawyer:
                // show pop up and save in firebase
                // saveReviewolawyer()
                openDialog();

                Toast.makeText(this, "Review lawyer", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    // dialog box where client leaves review of lawyer
    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }

    // read message to the recyclerview

    private void readMessages(final String myid, final String userid){
        mchat =  new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void applyTexts(String username, String comment) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String clientuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (username != null && comment != null){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("username", username);
            hashMap.put("comment", comment);
            hashMap.put("clientid", clientuid);
            reference.child("Users").child("Lawyers").child(userid).child("reviews").child(clientuid).setValue(hashMap);
        }else{
            Toast.makeText(this, "Please Enter a comment", Toast.LENGTH_SHORT).show();
        }


    }
}