package com.example.notesappfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShowFragment : Fragment() {

    private lateinit var et_Title: EditText
    private lateinit var et_Note: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnBack : Button
    private lateinit var btnDelete : Button

    private var db: FirebaseFirestore = Firebase.firestore

    private var id = ""
    var title11 = ""
    var note11 = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show, container, false)

        et_Title = view.findViewById(R.id.et_Title)
        et_Note = view.findViewById(R.id.et_Note)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnBack = view.findViewById(R.id.btnBack)
        btnDelete = view.findViewById(R.id.btnDelete)

        btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_showFragment_to_mainFragment)
        }

        id = requireArguments().getString("id").toString()

        et_Title.setText(requireArguments().getString("title").toString())
        et_Note.setText(requireArguments().getString("note").toString())

        btnUpdate.setOnClickListener {

            title11 = et_Title.text.toString()
            note11 = et_Note.text.toString()

            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id == id){
                            db.collection("notes").document(id).update("note", note11)
                            db.collection("notes").document(id).update("title", title11)
                        }
                    }
                    Navigation.findNavController(view).navigate(R.id.action_showFragment_to_mainFragment)
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "Error adding document $exception")
                }
        }

        btnDelete.setOnClickListener {

            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id == id){
                            db.collection("notes").document(id).delete()
                        }
                    }
                    Navigation.findNavController(view).navigate(R.id.action_showFragment_to_mainFragment)
                }
                .addOnFailureListener { exception ->
                    Log.d("Firebase", "Error adding document $exception")
                }
        }
        return view
    }


}