package com.example.vinnikov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vinnikov.databinding.ActivityDescriptionBinding
import com.squareup.picasso.Picasso

class Description : AppCompatActivity() {
    lateinit var binding: ActivityDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("car") as Car
        Picasso.get().load(item.link).fit().into(binding.descImage)

        binding.descName.setText(item.name)
        binding.descDesc.setText(item.desc)


        binding.descPrice.setText(item.price)




    }
}