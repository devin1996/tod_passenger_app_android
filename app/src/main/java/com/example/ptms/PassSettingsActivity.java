package com.example.ptms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PassSettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;

    private Uri imageUri;
    private String myUrl = "";
    //private StorageTask uploadTask;
    //private StorageReference storageProfilePictureRef;
    private String checker="";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_settings);

        mAuth = FirebaseAuth.getInstance();

        //storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        fullNameEditText = (EditText) findViewById(R.id.settings_full_name);
        userPhoneEditText = (EditText) findViewById(R.id.settings_phone_number);
        addressEditText = (EditText) findViewById(R.id.settings_address);

        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update__settings_btn);
//
//        //userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);
//
        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                if (checker.equals("clicked"))
////                {
////                    userInfoSaved();
////                }
////                else
////                {
                updateOnlyUserInfo(mAuth);
////                }
            }
        });
    }


//        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                checker = "clicked";
//
//                CropImage.activity(imageUri)
//                        .setAspectRatio(1,1)
//                        .start(PassSettingsActivity.this);
//            }
//        });
//    }

    private void updateOnlyUserInfo(FirebaseAuth firebaseAuth ){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("name", fullNameEditText.getText().toString());
            userMap.put("address", addressEditText.getText().toString());
            userMap.put("phone No", userPhoneEditText.getText().toString());
            //ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
            ref.child("User").child("Passenger")
                    .child(user.getUid())
                    .setValue(userMap);

        }

        startActivity(new Intent(PassSettingsActivity.this, PasMenuActivity.class));
        Toast.makeText(PassSettingsActivity.this, "Profile Info Updated Succssfully", Toast.LENGTH_SHORT).show();
        finish();
    }

//    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//            HashMap<String, Object> mHashmap = new HashMap<>();
//            mHashmap.put("E mail", pasEmail);
//
//            ref.child("User").child("Passenger").child(user.getUid()).setValue(mHashmap);
//        }
//    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
//            profileImageView.setImageURI(imageUri);
//        }
//        else
//        {
//            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(PassSettingsActivity.this, PasMenuActivity.class));
//            finish();
//        }
//    }

//    private void userInfoSaved() {
//        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
//        {
//            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
//        {
//            Toast.makeText(this, "Addres is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
//        {
//            Toast.makeText(this, "Mobile no is mandatory", Toast.LENGTH_SHORT).show();
//        }
//        else if (checker.equals("clicked"))
//        {
//            uploadImage();
//        }
//
//
//    }

//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait, while we are updating your account information");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePictureRef
//                    .child(user.getUid() + ".jpg");
//
//            uploadTask = fileRef.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//                    return fileRef.getDownloadUrl();
//                }
//            })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful())
//                            {
//                                Uri downloadUrl = task.getResult();
//                                myUrl = downloadUrl.toString();
//
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                                FirebaseUser user = mAuth.getCurrentUser();
//
//                                HashMap<String, Object> userMap = new HashMap<>();
//                                userMap.put("name", fullNameEditText.getText().toString());
//                                userMap.put("address", addressEditText.getText().toString());
//                                userMap.put("phoneOrder", userPhoneEditText.getText().toString());
//                                userMap.put("image", myUrl);
//                                ref.child("User").child("Passenger").child(user.getUid()).updateChildren(userMap);
//
//                                progressDialog.dismiss();
//
//                                startActivity(new Intent(PassSettingsActivity.this, PasMenuActivity.class));
//                                Toast.makeText(PassSettingsActivity.this, "Profile Info Updated", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(PassSettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//        else {
//            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
//        }
//
//
//
//    }

//    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("User").child("Passenger").child(user.getUid());
//
//        UsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    if (dataSnapshot.child("image").exists())
//                    {
//                        String image = dataSnapshot.child("image").getValue().toString();
//                        String name = dataSnapshot.child("name").getValue().toString();
//                        String phone = dataSnapshot.child("phone").getValue().toString();
//                        String address = dataSnapshot.child("address").getValue().toString();
//
//                        Picasso.get().load(image).into(profileImageView);
//                        fullNameEditText.setText(name);
//                        userPhoneEditText.setText(phone);
//                        addressEditText.setText(address);
//
//
//                    }
//                }
//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
