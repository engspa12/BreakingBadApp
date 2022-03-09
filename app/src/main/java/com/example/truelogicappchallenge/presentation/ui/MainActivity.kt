package com.example.truelogicappchallenge.presentation.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.databinding.ActivityMainBinding
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListCharactersAdapter.ItemClickedInterface {

    private val viewmodel: ListCharactersViewModel by viewModels<ListCharactersViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setObservers()
        getListData()
    }

    private fun setObservers(){

        viewmodel.listCharacters.observe(this, Observer { data ->
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = ListCharactersAdapter(data,this)
        })

        viewmodel.progressBar.observe(this, Observer {
            it?.let {
               showProgressBar(it)
            }
        })

        viewmodel.emptyView.observe(this, Observer {
            it?.let {
                showEmptyView(it)
            }
        })

        viewmodel.errorMessage.observe(this, Observer {
            it?.let {
                setErrorMessage(it)
            }
        })

    }

    private fun showEmptyView(show: Boolean) {
        binding.emptyView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setErrorMessage(message: String) {
        binding.emptyView.text = message
    }

    private fun showProgressBar(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getListData(){
        viewmodel.getListCharacters()
    }

    override fun onClickedItem(position: Int) {
        viewmodel.saveItemAsFavorite(position)
    }
}