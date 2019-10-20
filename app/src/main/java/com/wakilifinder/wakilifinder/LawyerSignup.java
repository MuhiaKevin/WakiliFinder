package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LawyerSignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText emailfield, usernamefield, passwfield, p105numberfield,praticenumberfield,confirmpassfield;
    private Button createAccbtn;
    private ImageView mProfileImage;
    private Uri resultUri ;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private static final String TAG = "MUNYAMUNYA";
    public Spinner spinner;
    public String county;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_signup);

        emailfield = findViewById(R.id.lawyeremailfield);
        usernamefield = findViewById(R.id.lawyerusernamefield);
        p105numberfield = findViewById(R.id.p105number);
        praticenumberfield = findViewById(R.id.practiceNumber);
        passwfield = findViewById(R.id.passwfield);
        confirmpassfield = findViewById(R.id.confirmfield);
        createAccbtn = findViewById(R.id.lawyerSignupbtn1);
        mProfileImage = findViewById(R.id.profileImage);

        spinner = findViewById(R.id.countyspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.counties,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        createAccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailfield.getText().toString();
                final String p105strng = p105numberfield.getText().toString();
                final String username = usernamefield.getText().toString();
                final String practicenumstrng =  praticenumberfield.getText().toString();

                // password must have a length of 6 alphanumeric characters
                final String password = passwfield.getText().toString();
                final String confirmpassw = confirmpassfield.getText().toString();

                // validating inputs

                if (username.isEmpty()) {
                    usernamefield.setError("Required! ");
                    usernamefield.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    emailfield.setError("Required! ");
                    emailfield.requestFocus();
                    return;
                }
                if (p105strng.isEmpty()) {
                    p105numberfield.setError("Required!");
                    p105numberfield.requestFocus();
                    return;
                }

                if (practicenumstrng.isEmpty()) {
                    praticenumberfield.setError("Required!");
                    praticenumberfield.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwfield.setError("Required!");
                    passwfield.requestFocus();
                    return;
                }

                if (confirmpassw.isEmpty()) {
                    confirmpassfield.setError("Required!");
                    confirmpassfield.requestFocus();
                    return;
                }

                if(!TextUtils.equals(password,confirmpassw)){
                    confirmpassfield.setError("Passwords don't match!");
                    confirmpassfield.requestFocus();
                    return;
                }



                final ProgressDialog progressDialog = new ProgressDialog(LawyerSignup.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Registering...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show();


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(LawyerSignup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(LawyerSignup.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }

                            else{

                                if(resultUri != null){
                                    final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Users").child("Lawyers").child(email);
                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                                    byte[] data = baos.toByteArray();

                                    filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("Users").child("Lawyers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    HashMap<String,String> hashMap = new HashMap<>();

                                                    hashMap.put("imageurl", String.valueOf(uri));
                                                    hashMap.put("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    hashMap.put("email", String.valueOf(email));
                                                    hashMap.put("username", String.valueOf(username));
                                                    hashMap.put("p105number", String.valueOf(p105strng));
                                                    hashMap.put("county", String.valueOf(county).toUpperCase());
                                                    hashMap.put("practicenumber", String.valueOf(practicenumstrng));
                                                    hashMap.put("status", "online");
                                                    hashMap.put("password", String.valueOf(password));

                                                    imageStore.setValue(hashMap);
                                                }
                                            });
                                        }
                                    });


                                }

                                else{
                                    //finish();
                                    Toast.makeText(LawyerSignup.this, "No image Selected", Toast.LENGTH_SHORT).show();
                                }


                            progressDialog.dismiss();
                        }
                    }
                });

            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        county = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(),county, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}