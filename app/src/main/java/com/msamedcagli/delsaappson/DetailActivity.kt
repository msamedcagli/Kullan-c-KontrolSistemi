package com.msamedcagli.delsaappson

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var itemNameEditText: EditText
    private lateinit var itemImageView: ImageView
    private lateinit var deleteButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton
    private lateinit var currentItem: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        backButton = findViewById(R.id.backButton)
        itemNameEditText = findViewById(R.id.itemNameEditText)
        itemImageView = findViewById(R.id.itemImageView)
        deleteButton = findViewById(R.id.deleteButton)
        saveButton = findViewById(R.id.saveButton)

        backButton.setOnClickListener {
            onBackPressed()
        }

        try {
            currentItem = intent.getParcelableExtra<Item>("item") ?: throw IllegalStateException("Item not found")
            itemNameEditText.setText(currentItem.name)
            itemImageView.setImageResource(currentItem.imageResId)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        saveButton.setOnClickListener {
            val newName = itemNameEditText.text.toString()
            if (newName.isNotEmpty()) {
                val updatedItem = currentItem.copy(name = newName)
                val resultIntent = Intent()
                resultIntent.putExtra("item", updatedItem)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Item name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
            val resultIntent = Intent()
            resultIntent.putExtra("item", currentItem)
            setResult(RESULT_CANCELED, resultIntent)
            finish()
        }
    }
}
