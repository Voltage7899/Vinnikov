package com.example.vinnikov

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.vinnikov.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var tableName:String="User"
    private var database: DatabaseReference =FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registration.setOnClickListener {
            reg()
        }


        changeModeTable()
        binding.signSign.setOnClickListener {
            sign()
        }

    }

    fun changeModeTable(){
        binding.modeAdmin.setOnClickListener {

            tableName="Admin"
            binding.modeAdmin.visibility= View.GONE
            binding.modeUser.visibility= View.VISIBLE
        }
        binding.modeUser.setOnClickListener {

            tableName="User"
            binding.modeUser.visibility= View.GONE
            binding.modeAdmin.visibility= View.VISIBLE
        }

    }
    fun sign(){

        if(tableName=="User"){

            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@MainActivity, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.child(tableName).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: Buyer? = snapshot.child(binding.phoneSign.text.toString()).getValue(
                                Buyer::class.java
                            )

                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(binding.passSign.text.toString())) {
                                Toast.makeText(this@MainActivity, "Вы вошли как Юзер", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@MainActivity, AllCars::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@MainActivity, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
        else{
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@MainActivity, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.child(tableName).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: Buyer? = snapshot.child(binding.phoneSign.text.toString()).getValue(Buyer::class.java)


                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(
                                    binding.passSign.text.toString()))
                            {
                                Toast.makeText(this@MainActivity, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@MainActivity, ControlPanel::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@MainActivity, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }



    }
    fun reg(){
        val user=Buyer(binding.nameReg.text.toString(),binding.phoneReg.text.toString(),binding.passReg.text.toString())

        database.child("User").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.child(binding.phoneReg.text.toString()).exists()) {
                    database.child("User").child(binding.phoneReg.text.toString()).setValue(user)
                    Toast.makeText(this@MainActivity, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MainActivity, "Пользователь с такими данными уже есть", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}