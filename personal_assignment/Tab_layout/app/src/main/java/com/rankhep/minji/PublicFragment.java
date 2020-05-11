package com.rankhep.minji;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
