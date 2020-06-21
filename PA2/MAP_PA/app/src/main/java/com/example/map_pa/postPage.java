package com.example.map_pa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class postPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE = 777;
    private StorageReference mStorageRef;

    DrawerLayout drawerLayout;
    ImageView selectProfile;
    Uri currentImageUri;
    MenuItem navigationFullname, navigationBirthday, navigationEmail;
    String fullNameText = "", birthdayText = "", emailText = "", username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);


        ImageButton newPost = (ImageButton)findViewById(R.id.newPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(postPage.this, NewPost.class);
                newPostIntent.putExtra("Username", username);
                startActivity(newPostIntent);
            }
        });

        /* to use toolbar,
        1. add implementation
                implementation 'com.android.support:appcompat-v7:29.0.3'
           into build.gradle(Module:app)  !!!! version is same with buildToolsversion

        2. add toolbar in your layout
        3. Set toolbar when onCreate (you must import androidx.appcompat.widget.Toolbar
        4. If not Refactor -> Migrate to Androidx
         */

        Toolbar tb = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setAdapter(new myFragmentStateAdapter(this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Personal");
                        break;
                    case 1:
                        tab.setText("Public");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);

        View headerView = navigationView.getHeaderView(0);
        navigationFullname = (MenuItem)navigationView.getMenu().findItem(R.id.navigationFullname);
        navigationBirthday = (MenuItem)navigationView.getMenu().findItem(R.id.navigationBirthday);
        navigationEmail = (MenuItem)navigationView.getMenu().findItem(R.id.navigationEmail);

        TextView navUsername = (TextView) headerView.findViewById(R.id.drawer_username);
        selectProfile = (ImageView) headerView.findViewById(R.id.drawer_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
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
        drawerLayout.closeDrawer(GravityCompat.START);

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
    }

    public String getUsername() {
        return username;
    }
}
