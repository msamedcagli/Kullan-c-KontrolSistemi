package com.msamedcagli.delsaappson

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private var itemList = ArrayList<Item>()
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = RecyclerViewAdapter(itemList) { item ->
            try {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("item", item)
                startActivityForResult(intent, 1)
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            // Add a new item when floating button is clicked
            val newItem = Item(id=itemList.size+1,"Yeni Cihaz", R.drawable.cihaz)
            itemList.add(newItem)
            adapter.notifyItemInserted(itemList.size - 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            when (resultCode) {
                RESULT_OK -> {
                    val updatedItem = data?.getParcelableExtra<Item>("item")
                    if (updatedItem != null) {
                        val position = itemList.indexOfFirst { it.id == updatedItem.id }
                        if (position != -1) {
                            itemList[position] = updatedItem
                            adapter.notifyItemChanged(position)
                            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                RESULT_CANCELED -> {
                    val deletedItem = data?.getParcelableExtra<Item>("item")
                    if (deletedItem != null) {
                        val position = itemList.indexOfFirst { it.id == deletedItem.id }
                        if (position != -1) {
                            itemList.removeAt(position)
                            adapter.notifyItemRemoved(position)
                            Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
