package com.example.koffer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabTwo extends Fragment {
    public TabTwo() { }

    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_two, container, false);

        //uid = getCurrent.guid()

//        view.findViewById();

        // this   =>   getActivity()

        /*
            nclick(){
                saco los dato

                long =
                lat  =
                canti =

                String suitcaseKey = getReference().push().getKey();

                getReference().child("suitcase").child(suitcaseKey).setValue(suitcase);
                Ref().child("user-suicase").child(uid).child(suitcaseKey).setValue(true);

            }


         */

        return view;
    }

}
