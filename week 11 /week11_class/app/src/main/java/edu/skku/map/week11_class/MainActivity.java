package edu.skku.map.week11_class;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 777;
    private StorageReference mStorageRef;
    Uri currentImageUri;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check = false;
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        Button select = (Button)findViewById(R.id.selectimage);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        Button upload = (Button)findViewById(R.id.uploadimage);
        upload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(check) {
                    // set filename
//                    StorageReference ref = mStorageRef.child("parasite.jpg");
//                    StorageReference ref = mStorageRef.child(currentImageUri.getLastPathSegment());
                    StorageReference ref = mStorageRef.child("2018311658.jpg");
                    UploadTask uploadtask = ref.putFile(currentImageUri);

                    uploadtask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Failed to upload image");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity.this, "Upload Success!", Toast.LENGTH_LONG);
                        }
                    });
                }
            }
        });

        Button download = (Button)findViewById(R.id.downloadimage);
        download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                StorageReference ref = mStorageRef.child("frozen.jpg");
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
                        ImageView img = (ImageView)findViewById(R.id.imageview);
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            ImageView img = (ImageView)findViewById(R.id.imageview);
            TextView text1 = (TextView)findViewById(R.id.text);

            currentImageUri = data.getData();
            check = true;
            // set selected image
            img.setImageURI(data.getData());
            text1.setText(currentImageUri.toString());
        }
    }
}
