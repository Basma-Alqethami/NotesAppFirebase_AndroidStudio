package com.example.notesappfirebase.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfirebase.Firebase.Data
import com.example.notesappfirebase.Firebase.NotesViewModel
import com.example.notesappfirebase.R
import com.example.notesappfirebase.RV_Adapter.RVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var rvAdapter: RVAdapter
    private lateinit var recyclerView : RecyclerView

    private var list = ArrayList<Data>()
    private var db: FirebaseFirestore = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMain)

        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val t = document.data["title"]
                    val n = document.data["note"]

                    list.add(Data(document.id, t.toString(),n.toString()))
                    Log.d("cccccccc", "${document.data}")
                    RVUpdate()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "Error adding document $exception")
            }
    }

    fun RVUpdate() {
        rvAdapter = RVAdapter(list)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.AddMenu){
            val intent = Intent(this, AddNotes::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
