package com.example.notesappfirebase.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesappfirebase.Firebase.Data
import com.example.notesappfirebase.Firebase.NotesViewModel
import com.example.notesappfirebase.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException

class AddNotes : AppCompatActivity() {
    private lateinit var etTitleAdd: EditText
    private lateinit var edNoteAdd: EditText
    private lateinit var btnSave: Button
    private var db: FirebaseFirestore = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        etTitleAdd = findViewById(R.id.etTitleAdd)
        edNoteAdd = findViewById(R.id.edNoteAdd)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener { SavaData() }

    }

    private fun SavaData() {
        val textTitle = etTitleAdd.text.toString()
        val textNote = edNoteAdd.text.toString()

        try {
            if (textTitle.isNotEmpty()) {
                if (textNote.isNotEmpty()) {
                    NoteIsNotEmpty(textTitle, textNote)
                } else {
                    NoteIsEmpty(textTitle)
                }
            } else {
                Toast.makeText(this@AddNotes, "Please enter the title", Toast.LENGTH_SHORT).show()
            }
        }catch (e: IOException) {
            Toast.makeText(this@AddNotes, "something wrong please try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun NoteIsNotEmpty(textTitle: String, textNote: String) {

        val note = hashMapOf(
            "note" to textNote,
            "title" to textTitle
        )
        db.collection("notes")
            .add(note)
            .addOnSuccessListener { documentReference ->
                val intent = Intent(this@AddNotes, MainActivity::class.java)
                startActivity(intent)
                Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Error adding document $e")
            }
    }

    private fun NoteIsEmpty(textTitle: String)  {

        val note = hashMapOf(
            "note" to "",
            "title" to textTitle
        )
        db.collection("notes")
            .add(note)
            .addOnSuccessListener { documentReference ->
                val intent = Intent(this@AddNotes, MainActivity::class.java)
                startActivity(intent)
                Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Error adding document $e")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_to_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.BackMenu){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}