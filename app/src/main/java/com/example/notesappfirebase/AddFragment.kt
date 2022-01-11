package com.example.notesappfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.notesappfirebase.Activity.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddFragment : Fragment() {

    private lateinit var etTitleAdd: EditText
    private lateinit var edNoteAdd: EditText
    private lateinit var btnSave: Button
    private lateinit var btnBack : Button
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        etTitleAdd = view.findViewById(R.id.etTitleAdd)
        edNoteAdd = view.findViewById(R.id.edNoteAdd)
        btnSave = view.findViewById(R.id.btnSave)
        btnBack = view.findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_mainFragment)
        }

        btnSave.setOnClickListener {
            val textTitle = etTitleAdd.text.toString()
            val textNote = edNoteAdd.text.toString()

            try {
                if (textTitle.isNotEmpty()) {

                    val note = hashMapOf(
                        "note" to textNote,
                        "title" to textTitle
                    )
                    db.collection("notes")
                        .add(note)
                        .addOnSuccessListener { documentReference ->
                            Navigation.findNavController(view).navigate(R.id.action_addFragment_to_mainFragment)
                            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.d("Firebase", "Error adding document $e")
                        }

                } else {
                    Toast.makeText(requireContext(), "Please enter the title", Toast.LENGTH_SHORT).show()
                }
            }catch (e: IOException) {
                Toast.makeText(requireContext(), "something wrong please try again", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}