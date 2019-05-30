package com.example.koffer.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koffer.config.ConfigPaypal;
import com.example.koffer.R;
import com.example.koffer.model.Suitcase;
import com.example.koffer.view.activity.PaymentDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

public class ServiceFragment extends Fragment {

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.PAYPAL_CLIENT_ID);

    String amount = "10";

    View view;
    EditText name;
    EditText email;
    EditText phone;
    EditText dni;
    EditText quantity;
    EditText weight;
    EditText pickUpAddress;
    EditText deliveryAddress;
    Button btnService;

    private DatabaseReference mdatabase;

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start Paypal Service
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);

        mdatabase = FirebaseDatabase.getInstance().getReference();

        name = view.findViewById(R.id.registerName);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        dni = view.findViewById(R.id.dni);
        quantity = view.findViewById(R.id.quantity);
        weight = view.findViewById(R.id.kg);
        pickUpAddress = view.findViewById(R.id.pickUpAddress);
        deliveryAddress = view.findViewById(R.id.deliveryAddress);

        btnService = view.findViewById(R.id.service);

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newService();
            }
        });
        return view;
    }

    public void newService() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String dniString = dni.getText().toString();
        String quantity = this.quantity.getText().toString();
        String weight = this.weight.getText().toString();
        String pickUpAddress = this.pickUpAddress.getText().toString();
        String deliveryAddress = this.deliveryAddress.getText().toString();
        String carrierAsigned = "";

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(dniString) || !TextUtils.isEmpty(quantity) || !TextUtils.isEmpty(weight) || !TextUtils.isEmpty(pickUpAddress) || !TextUtils.isEmpty((deliveryAddress))) {
            String key = mdatabase.push().getKey();
            Suitcase suitCase = new Suitcase(uid, name, email, phone, dniString, quantity, weight, pickUpAddress, deliveryAddress,carrierAsigned);
            mdatabase.child("suitcase").child(key).setValue(suitCase);
            mdatabase.child("user-suitcase").child(uid).child(key).setValue(true);
            mdatabase.child("unassigned-suitcase").child(key).setValue(true);
            Toast.makeText(getActivity(), "Petición aceptada", Toast.LENGTH_LONG).show();
            processPayment();

        } else {
            Toast.makeText(getActivity(), "Debes completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR", "Pay to Koffer", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);
                        startActivity(new Intent(getActivity(), PaymentDetailsActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", amount)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
    }

}
