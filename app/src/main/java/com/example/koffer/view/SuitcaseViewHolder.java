package com.example.koffer.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.koffer.R;

public class SuitcaseViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView userEmail;
    public TextView suitcaseQuantity;
    public TextView suitcaseKG;

    public SuitcaseViewHolder(View item) {
        super(item);

        userName = item.findViewById(R.id.userName);
        userEmail = item.findViewById(R.id.userEmail);
        suitcaseQuantity = item.findViewById(R.id.suitecaseQuantity);
        suitcaseKG = item.findViewById(R.id.suitcaseKG);
    }
}
