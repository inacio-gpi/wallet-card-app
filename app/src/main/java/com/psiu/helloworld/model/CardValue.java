package com.psiu.helloworld.model;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class CardValue
{
    // private String id = UUID.randomUUID().toString();
    private String id;
    private String name;
    private String card_number;
    private Long limit;
    private Category category;

    public CardValue() {}

    public CardValue(String name, String card_number, Long limit, Category category) {
        this.name = name;
        this.card_number = card_number;
        this.limit = limit;
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void salvar(DatabaseReference reference) {
        // = FirebaseDatabase.getInstance().getReference();
        reference.child("cards").push().setValue(this);
        // reference.child("cards").child(getId()).setValue(this);
    }
}
