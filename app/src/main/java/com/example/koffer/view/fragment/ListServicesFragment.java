package com.example.koffer.view.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.koffer.R;
import com.example.koffer.model.Suitcase;
import com.example.koffer.view.SuitcaseViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.koffer.view.fragment.HomeCarrierFragment.SUITCASE_REFERENCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListServicesFragment extends Fragment {

    public static final String SUITCASE_REFERENCE = "suitcase";
    public DatabaseReference mReference;
    public FirebaseUser mUser;

    private String cardOrderId;

    public ListServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_services, container, false);
        setupComponents(view);
        return view;
    }

    private void setupComponents(View view) {
        mReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<Suitcase> options = new FirebaseRecyclerOptions.Builder<Suitcase>()
                .setIndexedQuery(setQuery(), mReference.child(SUITCASE_REFERENCE), Suitcase.class)
                .setLifecycleOwner(this)
                .build();

        RecyclerView recyclerView = view.findViewById(R.id.suitcase_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FirebaseRecyclerAdapter<Suitcase, SuitcaseViewHolder>(options) {
            @NonNull
            @Override
            public SuitcaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suitecase, parent, false);

                return new SuitcaseViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SuitcaseViewHolder holder, int position, @NonNull Suitcase suitcase) {
                final String suitcaseKey = getRef(position).getKey();

                orderId(suitcaseKey);

                holder.userName.setText(suitcase.getName());
                holder.userEmail.setText(suitcase.getEmail());
                holder.suitcaseQuantity.setText(suitcase.getQuantity());
                holder.suitcaseKG.setText(suitcase.getKg());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                });
            }
        });
    }

    private void openDialog() {
        final String uid = mUser.getUid();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Informacion")
                .setMessage("¿Ha finalizado este encargo?")
                .setPositiveButton("Entregado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Variable del usuario 'activeService' = false;
                        mReference.child("user-suitcase").child(uid).child(key).child("activeService");
                        // ID pedido = false; de esta manera no se mostrará en el recyclerView de los pedidos del transportista

                        delivered();
                    }
                })
                .setNegativeButton("Recogido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // BB.DD: cambiar atributo 'isCaught' = true;
                        // Cambiar estado del pedido a 'Recogido'; con un textView o lo que pinche quieras wey
                        collected();
                    }
                });
        builder.show();
    }

    Query setQuery(){
        return  mReference.child(SUITCASE_REFERENCE).limitToFirst(100);
    }

    public void orderId(String suitcaseKey){
        cardOrderId = suitcaseKey;
    }

    public void delivered(){
        String uid = FirebaseAuth.getInstance().getUid();


        Toast.makeText(getActivity(), "Encargo entregado", Toast.LENGTH_LONG).show();
    }

    public void collected(){
        String uid = FirebaseAuth.getInstance().getUid();


        Toast.makeText(getActivity(), "Encargo recogido", Toast.LENGTH_LONG).show();
    }

}
