package com.example.vinnikov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinnikov.databinding.ActivityControlPanelBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ControlPanel : AppCompatActivity() ,AdapterCars.ClickListener {
    lateinit var binding: ActivityControlPanelBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ListAdapter:AdapterCars?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityControlPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navLeftMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.add->{
                    startActivity(Intent(this,Add::class.java))
                }

            }
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }


        binding.recyclerAdmin.layoutManager=  LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        ListAdapter= AdapterCars(this)
        binding.recyclerAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =ListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Cars").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Cars").child(id.toString()).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerAdmin)
    }


    fun getData():ArrayList<Car>{



        val List=ArrayList<Car>()
        database.getReference("Cars").get().addOnSuccessListener {
            for (el in it.children){
                var car=el.getValue(Car::class.java)
                if(car!=null){
                    List.add(car)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }
    override fun onClick(car: Car) {
        startActivity(Intent(this, Redact::class.java).apply {
            putExtra("car",car)

        })
    }

    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}