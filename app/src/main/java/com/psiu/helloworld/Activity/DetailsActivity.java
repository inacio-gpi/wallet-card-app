package com.psiu.helloworld.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.psiu.helloworld.Activity.MainActivity;
import com.psiu.helloworld.R;

public class DetailsActivity extends AppCompatActivity {

    TextView tv_ApelidoDetail, tv_LimitDetail, tv_NumeroCartaoDetail;
    ImageView imagemCartaoDetail;
    String cardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        cardId = getIntent().getStringExtra("cardId");
        String apelidoDetail = getIntent().getStringExtra("apelidoDetail");
        String limitDetail = getIntent().getStringExtra("limitDetail");
        String numeroCartaoDetail = getIntent().getStringExtra("numeroCartaoDetail");
        String imagemDetail = getIntent().getStringExtra("imagemDetail");

        tv_ApelidoDetail = (TextView) findViewById(R.id.tvApelidoDetail);
        tv_LimitDetail = (TextView) findViewById(R.id.tvLimitDetail);
        tv_NumeroCartaoDetail = (TextView) findViewById(R.id.tvNumeroCartaoDetail);
        imagemCartaoDetail = findViewById(R.id.imagemCartaoDetail);

        Glide
                .with(this)
                .load(imagemDetail)
                .into(imagemCartaoDetail);


        tv_ApelidoDetail.setText(apelidoDetail);
        tv_LimitDetail.setText(limitDetail);
        tv_NumeroCartaoDetail.setText(numeroCartaoDetail);
    }

    /** Called when the user touches the button */
    public void removeCardDetail(View view) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        // reference.child("cards").push().setValue(this);

        reference.child("cards").child(cardId).setValue(null);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("card_id", cardId);
        startActivity(intent);
    }

    /** Called when the user touches the button */
    public void editCardDetail(View view) {
        Intent intent = new Intent(this, EditCardActivity.class);
        intent.putExtra("card_id", cardId);
        startActivity(intent);
    }
}