package com.example.notesappfirebase

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfirebase.Firebase.Data
import com.example.notesappfirebase.RV_Adapter.RVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    private lateinit var rvAdapter: RVAdapter
    private lateinit var recyclerView : RecyclerView

    private var list = ArrayList<Data>()
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.rvMain)

        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val t = document.data["title"]
                    val n = document.data["note"]

                    list.add(Data(document.id, t.toString(), n.toString()))
                    Log.d("cccccccc", "${document.data}")
                    rvAdapter = RVAdapter(this, list)
                    recyclerView.adapter = rvAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "Error adding document $exception")
            }
        return view
    }
}