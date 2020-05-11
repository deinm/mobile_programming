package edu.skku.map.exam_assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class PublicFragment extends Fragment {

    public PublicFragment() {
    }

    public static PublicFragment newInstance() {
        PublicFragment fragment = new PublicFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_public, container, false);
        return v;
    }
}
