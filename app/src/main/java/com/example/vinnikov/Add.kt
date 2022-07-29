package com.example.vinnikov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vinnikov.databinding.ActivityAddBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.random.Random

class Add : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addCar.setOnClickListener {

            val id = Random.nextInt(1,100000)

            val car=Car(id.toString(),binding.addName.text.toString(),binding.addDesc.text.toString(),binding.addPrice.text.toString(),binding.addLink.text.toString())

            database.getReference("Cars").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(car.id.toString()).exists()){
                        Toast.makeText(this@Add,"Уже есть", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Cars").child(id.toString()).setValue(car)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.addImage.setOnClickListener {
            try {
                Picasso.get().load(binding.addLink.text.toString()).fit().into(binding.addImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }

    }
}