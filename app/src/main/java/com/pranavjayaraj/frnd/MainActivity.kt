package com.pranavjayaraj.frnd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranavjayaraj.frnd.adapters.AudioAdapter
import com.pranavjayaraj.frnd.data.Audio

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


        private lateinit var audioViewModel: AudioViewModel
    var adapter = AudioAdapter()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.setHasFixedSize(true)
            recycler_view.adapter = adapter
            audioViewModel = ViewModelProviders.of(this).get(AudioViewModel::class.java)
            setupObserver()
            }


    private fun setupObserver()
    {

        audioViewModel.getAllAudio().observe(this, Observer { items ->
            items?.let {
                adapter.submitList(items)
            }
        })

    }

}

