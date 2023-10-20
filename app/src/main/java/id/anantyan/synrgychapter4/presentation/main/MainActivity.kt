package id.anantyan.synrgychapter4.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}