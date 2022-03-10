package com.example.truelogicappchallenge.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.presentation.model.CharacterView

class ListCharactersAdapter(
    private val listCharacters: List<CharacterView>,
    private val listener: ItemClickedInterface
): RecyclerView.Adapter<ListCharactersAdapter.ListCharacterHolder>() {

    private var innerList = listOf<CharacterView>()

    interface ItemClickedInterface {
        fun onClickedItem(position: Int)
    }

    init {
        innerList = listCharacters
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharacterHolder {
        return ListCharacterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ListCharacterHolder, position: Int) {
        holder.setValues(innerList, position, listener)
    }

    override fun getItemCount(): Int {
        return innerList.size
    }

    fun setList(newList: List<CharacterView>){
        innerList = newList
        notifyDataSetChanged()
    }

    class ListCharacterHolder(view: View): RecyclerView.ViewHolder(view) {

       fun setValues(list: List<CharacterView>, position: Int, listener: ItemClickedInterface){
           val imageURL: ImageView = itemView.findViewById(R.id.photoImageView)
           val favoriteImageView: ImageView = itemView.findViewById(R.id.favoriteIcon)
           val name: TextView = itemView.findViewById(R.id.nameTextView)
           val nickname: TextView = itemView.findViewById(R.id.nicknameTextView)

           Glide.with(itemView.context)
               .load(list[position].img)
               .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
               .into(imageURL)

           val favoriteIcon = if(list[position].isFavorite) {
               favoriteImageView.tag = R.drawable.ic_favorite
               R.drawable.ic_favorite
           } else {
               favoriteImageView.tag = R.drawable.ic_no_favorite
               R.drawable.ic_no_favorite
           }

           Glide.with(itemView.context)
               .load(favoriteIcon)
               .into(favoriteImageView)

           name.text = list[position].name
           nickname.text = list[position].nickname

           favoriteImageView.setOnClickListener {
               listener.onClickedItem(position)
           }
       }

    }
}