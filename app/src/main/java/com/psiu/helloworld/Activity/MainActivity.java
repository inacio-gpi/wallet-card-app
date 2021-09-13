package com.psiu.helloworld.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.psiu.helloworld.R;
import com.psiu.helloworld.RecyclerViewCard;
import com.psiu.helloworld.model.Card;
import com.psiu.helloworld.model.CardValue;
import com.psiu.helloworld.model.Category;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewCard.ItemClickListener {

    private static final String TAG = "MainActivity";
    ArrayList<CardValue> array_cards;
    RecyclerViewCard adapter;
    // Card retorno = null;
    Card card;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.cards_list);

        array_cards = new ArrayList<>();
        // READ FireBase
        // configura o RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this,1)); // Visualização em grid com 1 coluna
        adapter = new RecyclerViewCard(this, array_cards);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance().getReference();

        database.child("cards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                array_cards.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CardValue cardValue = dataSnapshot.getValue(CardValue.class);
                    // card.setCards((List<CardValue>) cardValue);
                    array_cards.add(cardValue);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // READ FireBase

        /*
        CardService service = new CardService();
        try {
            retorno = service.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        // configura o RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this,1)); // Visualização em grid com 1 colunas
        // adapter = new RecyclerViewCard(this, (ArrayList<CardValue>) retorno.getCards());
        adapter = new RecyclerViewCard(this, array_cards);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
         */

        // click button
        Button button = (Button) findViewById(R.id.button_add_card);
        button.setOnClickListener(v -> {
            // verifica se existe cartçao na lista

            CardValue new_card = new CardValue();
            new_card.setCard_number("0000");
            Category category = new Category();
            category.setImage_path("https://www.coopercard.com.br/Portal/Static/Imagem/Cards/18022021_07_home_ticket_ali.png");
            category.setBackground_color("#21B354");
            new_card.setCategory(category);
            new_card.setName("Novo Cartão");
            new_card.setLimit(300L);
            new_card.salvar(database);
            // adapter.adicionarCard(new_card);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FirebaseDatabase cards = FirebaseDatabase.
    }

    @Override
    public void onItemClick(View view, int position) {
        String apelidoDetail = adapter.getItem(position).getName();
        Long limitDetail = adapter.getItem(position).getLimit();
        String numeroCartaoDetail = adapter.getItem(position).getCard_number();
        String imagemDetail = adapter.getItem(position).getCategory().getImage_path();

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("cardId", Integer.toString(position));
        intent.putExtra("apelidoDetail", apelidoDetail);
        intent.putExtra("limitDetail", limitDetail);
        intent.putExtra("numeroCartaoDetail", numeroCartaoDetail);
        intent.putExtra("imagemDetail", imagemDetail);
        startActivity(intent);
    }

}