package com.example.map_pa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String username;

    private StorageReference mStorageRef;
    private ListView listView;
    private ArrayList memos;

    public PublicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicFragment newInstance(String param1, String param2) {
        PublicFragment fragment = new PublicFragment();
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
//        return inflater.inflate(R.layout.fragment_public, container, false);

        View view = inflater.inflate(R.layout.fragment_public, container, false);

        listView = (ListView) view.findViewById(R.id.publicPostList);
        listView.setAdapter(new PublicFragment.MyListAdapter(this.getContext()));

        return view;
    }

    private class MyListAdapter extends BaseAdapter {

        private Context context;

        public MyListAdapter(Context context) {
            this.context = context;

            // load public posts
            DatabaseReference publicPosts = FirebaseDatabase.getInstance().getReference().child("post_values");
            memos = new ArrayList<MemoItem>();

            ValueEventListener fullNameEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        String posterName = key.split("_public_")[0];

                        ArrayList postingValues = new ArrayList<String>();
                        for(DataSnapshot dsvalue : ds.getChildren()) {
                            try{
                                String value = dsvalue.getValue(String.class);
                                postingValues.add(value);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                        // use if you want to get data from other Activity
                        postPage activity = (postPage) getActivity();
                        username = activity.getUsername();
                        int size = postingValues.size();
                        MemoItem temp= new MemoItem();
                        if(size==2){
                            temp = new MemoItem(posterName, postingValues.get(0).toString(), postingValues.get(1).toString(), "");
                        }
                        else if(size==3){
                            String imageID = postingValues.get(1).toString();
                            temp = new MemoItem(posterName, postingValues.get(0).toString(), postingValues.get(2).toString(), imageID);
                        }

                        if(key.contains("public")){
                            memos.add(temp);
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            publicPosts.addListenerForSingleValueEvent(fullNameEventListener);
            // end of loading public posts

//            int num = getCount();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View itemLayoutLinear = inflater.inflate(R.layout.item_layout, listView, false);

//            for(int i=0; i<num; i++){
//                getView(i, itemLayoutLinear, listView);
//            }
        }

        @Override
        public int getCount() {
            return memos.size();
        }

        @Override
        public Object getItem(int i) {
            return memos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if ( view == null ) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_layout, viewGroup, false);
            }

            TextView usernameTextView = (TextView) view.findViewById(R.id.userName);
            MemoItem singleItem = (MemoItem)getItem(i);

            String postedUsername = singleItem.getUsername();
            usernameTextView.setText(postedUsername);

            TextView contentTextView = (TextView) view.findViewById(R.id.postedContent);
            contentTextView.setText(singleItem.getContent());

            TextView tagTextView = (TextView) view.findViewById(R.id.postedHashtag);
            tagTextView.setText(singleItem.getHashtag());

            String specificimageID = singleItem.getImageID();
            final ImageView postedImageContent = (ImageView) view.findViewById(R.id.postedImageContent);
            final ImageView userMainProfile = (ImageView) view.findViewById(R.id.userProfile);

            // set user profile image
            mStorageRef = FirebaseStorage.getInstance().getReference("Images");
            StorageReference ref = mStorageRef.child(postedUsername+".jpg");

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

                    userMainProfile.setImageBitmap(bitmap);
                }
            });
            // end of setting profile image

            // If image exists, set image
            if(specificimageID.length() != 0){
                mStorageRef = FirebaseStorage.getInstance().getReference("PostImages");
                StorageReference postref = mStorageRef.child(specificimageID+".jpg");

                postref.getBytes(ONE_MEGABYTE).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failed to download image");
                    }
                }).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        postedImageContent.setImageBitmap(bitmap);
                    }
                });
                // end of changing image
            }

            return view;
        }
    }

}
