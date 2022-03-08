package com.example.truelogicappchallenge.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListCharactersAdapter.ItemClickedInterface {

    private val viewmodel: ListCharactersViewModel by viewModels<ListCharactersViewModel>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)

        setObservers()
        getListData()
    }

    private fun setObservers(){

        viewmodel.listCharacters.observe(this, Observer { data ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = ListCharactersAdapter(data,this)
        })

    }

    private fun getListData(){
        viewmodel.getListCharacters()
    }

    override fun onClickedItem(position: Int) {
        viewmodel.saveItemAsFavorite(position)
    }
}