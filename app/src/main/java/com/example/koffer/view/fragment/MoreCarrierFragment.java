package com.example.koffer.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koffer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreCarrierFragment extends Fragment {


    public MoreCarrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_carrier, container, false);
        return view;
    }
}
