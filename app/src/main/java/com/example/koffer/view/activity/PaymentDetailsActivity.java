package com.example.koffer.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.koffer.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    TextView txtId, txtState, txtAmount;
    Button backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = findViewById(R.id.txtId);
        txtState = findViewById(R.id.txtStatus);
        txtAmount = findViewById(R.id.txtAmount);
        backToHome = findViewById(R.id.backToHome);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            verDetalles(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailsActivity.this, UserViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void verDetalles(JSONObject response, String amount) {
        try {
            txtId.setText("ID: " + response.getString("id"));
            txtState.setText("Estado de pago: " + response.getString("state"));
            txtAmount.setText("Amount: " + amount + "€");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
