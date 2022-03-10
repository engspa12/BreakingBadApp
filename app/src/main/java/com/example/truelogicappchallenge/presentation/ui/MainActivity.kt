package com.example.truelogicappchallenge.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.truelogicappchallenge.databinding.ActivityMainBinding
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListCharactersAdapter.ItemClickedInterface {

    private val viewmodel: ListCharactersViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListCharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setRecyclerView()
        setObservers()
        getListCharacters()
    }

    private fun setObservers(){

        viewmodel.listCharacters.observe(this) { data ->
            adapter.setList(data)
        }

        viewmodel.progressBar.observe(this) {
            it?.let {
                showProgressBar(it)
            }
        }

        viewmodel.emptyView.observe(this) {
            it?.let {
                showEmptyView(it)
            }
        }

        viewmodel.container.observe(this) {
            it?.let {
                showListCharactersContainer(it)
            }
        }

        viewmodel.errorMessage.observe(this) {
            it?.let {
                setErrorMessage(it)
            }
        }

    }

    private fun setRecyclerView(){
        adapter = ListCharactersAdapter(listOf(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setErrorMessage(message: String) {
        binding.emptyView.text = message
    }

    private fun showEmptyView(show: Boolean) {
        binding.emptyView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showProgressBar(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showListCharactersContainer(show: Boolean) {
        binding.recyclerView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getListCharacters(){
        viewmodel.getListCharacters()
    }

    override fun onClickedItem(position: Int) {
        viewmodel.saveItemAsFavorite(position)
    }
}