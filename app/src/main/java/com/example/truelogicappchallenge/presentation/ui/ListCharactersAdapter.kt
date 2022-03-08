package com.example.truelogicappchallenge.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.presentation.model.CharacterView

class ListCharactersAdapter(
    val listCharacters: List<CharacterView>,
    val listener: ItemClickedInterface
): RecyclerView.Adapter<ListCharactersAdapter.ListCharacterHolder>() {

    interface ItemClickedInterface {
        fun onClickedItem(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharacterHolder {
        return ListCharacterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ListCharacterHolder, position: Int) {
        holder.setValues(listCharacters, position, listener)
    }

    override fun getItemCount(): Int {
        return listCharacters.size
    }

    class ListCharacterHolder(view: View): RecyclerView.ViewHolder(view) {

       fun setValues(list: List<CharacterView>, position: Int, listener: ItemClickedInterface){
           val imageURL: ImageView = itemView.findViewById(R.id.imageView)
           val favoriteImage: ImageView = itemView.findViewById(R.id.favorite_img)
           val name: TextView = itemView.findViewById(R.id.textView)
           val nickname: TextView = itemView.findViewById(R.id.textView2)

           Glide.with(itemView.context)
               .load(list[position].img)
               .into(imageURL)

           if(list[position].favorite) {
               Glide.with(itemView.context)
                   .load(R.drawable.ic_favorite)
                   .into(favoriteImage)
           } else {
               Glide.with(itemView.context)
                   .load(R.drawable.ic_no_favorite)
                   .into(favoriteImage)
           }

           name.text = list[position].name
           nickname.text = list[position].nickname

           favoriteImage.setOnClickListener {
               listener.onClickedItem(position)
           }
       }

    }
}