package com.example.map_pa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewPost extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE = 777;
    private static final int PICK_POST_IMAGE = 888;
    private StorageReference mStorageRef, mPostRef;
    private DatabaseReference mPosttextRef;

    DrawerLayout drawerLayout;
    ImageView selectProfile;
    Uri currentImageUri, postImageUri;
    MenuItem navigationFullname, navigationBirthday, navigationEmail;
    String fullNameText = "", birthdayText = "", emailText = "", username = "";

    EditText postContent, postTags;
    ImageButton postImage;
    CheckBox publicPost;

    String contentText="", tagText="";
    boolean isPublic=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        postContent = findViewById(R.id.postContent);
        postTags = (EditText)findViewById(R.id.postTags);
        publicPost = (CheckBox)findViewById(R.id.publicPost);

        Button createPost = (Button)findViewById(R.id.createPost);
        postImage = (ImageButton)findViewById(R.id.postImage);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_POST_IMAGE);
            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(postContent.getText().length() == 0){
                    String msg = "Please input contents";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    return;
                }

                isPublic = publicPost.isChecked();
                contentText = postContent.getText().toString();
                tagText = postTags.getText().toString();

                // saving post & image
                postFirebaseDatabase2(true);
                // end of saving post

                Intent createPostIntent = new Intent(NewPost.this, postPage.class);
                createPostIntent.putExtra("Username", username);
                startActivity(createPostIntent);
            }
        });

        Toolbar tb = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);

        // get navigation items
        View headerView = navigationView.getHeaderView(0);
        navigationFullname = (MenuItem)navigationView.getMenu().findItem(R.id.navigationFullname);
        navigationBirthday = (MenuItem)navigationView.getMenu().findItem(R.id.navigationBirthday);
        navigationEmail = (MenuItem)navigationView.getMenu().findItem(R.id.navigationEmail);

        TextView navUsername = (TextView) headerView.findViewById(R.id.drawer_username);
        selectProfile = (ImageView) headerView.findViewById(R.id.drawer_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mPostRef = FirebaseStorage.getInstance().getReference("PostImages");
        mPosttextRef = FirebaseDatabase.getInstance().getReference();
        selectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        // Change Text in Drawer
        if(getIntent().getExtras() != null){
            Intent signupIntent = getIntent();
            username = signupIntent.getStringExtra("Username");
            navUsername.setText(username);

            DatabaseReference dbFullName = FirebaseDatabase.getInstance().getReference().child("user_info").child(username).child("fullname");
            DatabaseReference dbBirthday = FirebaseDatabase.getInstance().getReference().child("user_info").child(username).child("birthday");
            DatabaseReference dbEmail = FirebaseDatabase.getInstance().getReference().child("user_info").child(username).child("email");

            // dbfullname
            ValueEventListener fullNameEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String database_fullname = dataSnapshot.getValue().toString();
                    fullNameText = database_fullname;
                    navigationFullname.setTitle(fullNameText);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbFullName.addListenerForSingleValueEvent(fullNameEventListener);
            // end of getting fullname data

            // dbbirthday
            ValueEventListener birthdayEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String database_birthday = dataSnapshot.getValue().toString();
                    birthdayText = database_birthday;
                    navigationBirthday.setTitle(birthdayText);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbBirthday.addListenerForSingleValueEvent(birthdayEventListener);
            // end of getting birthday data

            // dbemail
            ValueEventListener emailEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String database_email = dataSnapshot.getValue().toString();
                    emailText = database_email;
                    navigationEmail.setTitle(emailText);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbEmail.addListenerForSingleValueEvent(emailEventListener);
            // end of getting birthday data
        }
        // End of Drawer

        // If image exists in db, change image
        StorageReference ref = mStorageRef.child(username+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;

        ref.getBytes(ONE_MEGABYTE).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failed to download image");
            }
        }).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                selectProfile.setImageBitmap(bitmap);
            }
        });
        // end of changing image

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, tb, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        closeDrawer();

        switch (item.getItemId()){
            case R.id.navigationBirthday:
                break;

            case R.id.navigationEmail:
                break;

            case R.id.navigationFullname:
                break;
        }


        return false;
    }

    private void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            currentImageUri = data.getData();
            // set selected image
            selectProfile.setImageURI(data.getData());

            // set filename
            StorageReference ref = mStorageRef.child(username+".jpg");
            UploadTask uploadtask = ref.putFile(currentImageUri);

            uploadtask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Fail","upload image");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Success","upload image");
                }
            });
        }
        else if(requestCode == PICK_POST_IMAGE){
            Log.d("Image upload", "success");
            postImageUri = data.getData();
            // set selected image
            postImage.setImageURI(postImageUri);
        }
    }

    public void postFirebaseDatabase2(boolean add){
        // save image file
        String imageId;
        if(postImageUri != null) {
            imageId = UUID.randomUUID().toString();

            StorageReference ref = mPostRef.child(imageId + ".jpg");
            UploadTask uploadtask = ref.putFile(postImageUri);

            uploadtask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Fail","upload image");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Success","upload image");
                }
            });
        }
        else{
            imageId = null;
        }
        // end of saving image file

        // save post
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost2 post = new FirebasePost2(contentText, tagText, imageId, isPublic);
            postValues = post.toMap();
        }

        String type = "";
        if(isPublic){
            type="public";
        }
        else{
            type="personal";
        }

        String postID = UUID.randomUUID().toString();
        childUpdates.put("post_values/"+username+"_"+type+"_"+postID, postValues);
        mPosttextRef.updateChildren(childUpdates);
    }
}
