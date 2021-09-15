package com.psiu.helloworld.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.psiu.helloworld.R;
import com.psiu.helloworld.model.CardValue;

public class EditCardActivity extends AppCompatActivity {

    DatabaseReference database;
    ImageView ivCardImage;
    TextView tvCardNameEdit;
    EditText etCardNumberEdit, etCardNameEdit, etCardLimitEdit, etCardSenhaEdit, etCardColorEdit;
    CardValue cardValue;
    String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        cardId = getIntent().getStringExtra("card_id");

        ivCardImage = findViewById(R.id.ivCardImage);
        tvCardNameEdit = findViewById(R.id.tvCardNameEdit);
        etCardNameEdit = findViewById(R.id.etCardNameEdit);
        etCardNumberEdit = findViewById(R.id.etCardNumberEdit);
        etCardLimitEdit = findViewById(R.id.etCardLimitEdit);
        etCardSenhaEdit = findViewById(R.id.etCardSenhaEdit);
        etCardColorEdit = findViewById(R.id.etCardColorEdit);

        // READ FireBase
        database = FirebaseDatabase.getInstance().getReference();
        database.child("cards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(cardId)){
                        cardValue = dataSnapshot.getValue(CardValue.class);
                    }
                }

                Glide
                    .with(EditCardActivity.this)
                    .load(cardValue.getCategory().getImage_path())
                    .into(ivCardImage);
                ivCardImage.setBackgroundColor(Color.parseColor(cardValue.getCategory().getBackground_color()));

                tvCardNameEdit.setText(cardValue.getName());
                etCardNameEdit.setText(cardValue.getName());
                etCardNumberEdit.setText(cardValue.getCard_number());
                etCardLimitEdit.setText(cardValue.getLimit().toString());
                // etCardSenhaEdit.setText(cardValue.get);
                etCardColorEdit.setText(cardValue.getCategory().getBackground_color());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /** Called when the user touches the button */
    public void salvarEditCard(View view) {
        /**
            Intent intent = new Intent(this, EditCardActivity.class);
            intent.putExtra("card_id", cardId);
            startActivity(intent);
         */
        String new_etCardNameEdit = etCardNameEdit.getText().toString();
        String new_etCardNumberEdit = etCardNumberEdit.getText().toString();
        Long new_etCardLimitEdit = Long.parseLong(etCardLimitEdit.getText().toString());
        // String new_etCardSenhaEdit = etCardSenhaEdit.getText().toString();
        String new_etCardColorEdit = etCardColorEdit.getText().toString();

        database.child("cards").child(cardId).child("card_number").setValue(new_etCardNumberEdit);
        database.child("cards").child(cardId).child("limit").setValue(new_etCardLimitEdit);
        database.child("cards").child(cardId).child("name").setValue(new_etCardNameEdit);
        database.child("cards").child(cardId).child("category").child("background_color").setValue(new_etCardColorEdit);
        // database.child("cards").child(cardId).child("category").child("type").setValue();


    }
}