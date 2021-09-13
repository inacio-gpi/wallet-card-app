package com.psiu.helloworld;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.psiu.helloworld.model.CardValue;

import java.util.ArrayList;

public class RecyclerViewCard extends RecyclerView.Adapter<RecyclerViewCard.ViewHolder> {

    private ArrayList<CardValue> mListaDeNomes; // lista de informações
    private LayoutInflater mInflater; // layout
    private ItemClickListener mClickListener;// precisamos disso para reconhecer cliques no adaper

    // construtor para passar os dados
    public RecyclerViewCard(Context context, ArrayList<CardValue> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mListaDeNomes = data;
    }

    // infla o layout da linha de xml quando necessário
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    // liga os dados ao TextView em cada linha
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url = mListaDeNomes.get(position).getCategory().getImage_path();
        String card_background = mListaDeNomes.get(position).getCategory().getBackground_color();
        // String card_background = "#21B354";

        Glide
            .with(holder.ImageViewCard_image)
            .load(url)
            .into(holder.ImageViewCard_image);
        holder.ImageViewCard_image.setBackgroundColor(Color.parseColor(card_background));

        String nome = mListaDeNomes.get(position).getName();

        String card_number = mListaDeNomes.get(position).getCard_number();
        Long card_limit = mListaDeNomes.get(position).getLimit();
        holder.textViewName.setText(nome);

        holder.textViewCardNumber.setText("**** " + card_number);
        holder.textViewCardNumber.setTextColor(Color.parseColor(card_background));

        holder.textViewLimit.setText(card_limit.toString());
    }
    // retorna o total de linhas
    @Override
    public int getItemCount() {
        return mListaDeNomes.size();
    }

    //metodo para remover items
    public void removerCard(int posicao){
        mListaDeNomes.remove(posicao); //remove o item na posicao desejada
        notifyDataSetChanged();// notifica que meus items foi alterado
    }
    //metodo para adicionar items
    public void adicionarCard(CardValue card){
        mListaDeNomes.add(card); //adiciona o card na ultima posicao
        notifyDataSetChanged();
    }


    // armazena e recicla as visualizações à medida que elas são deslizadas para fora da tela
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName, textViewCardNumber, textViewLimit;
        ImageView ImageViewCard_image;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.card_name);
            textViewCardNumber = itemView.findViewById(R.id.card_number);
            textViewLimit = itemView.findViewById(R.id.card_limit);
            ImageViewCard_image = itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // método de conveniência para obter dados na posição de clique
    public CardValue getItem(int id) {
        return mListaDeNomes.get(id);
    }

    // permite que os eventos de cliques sejam capturados
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // interface método para responder a eventos de cliques
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}