package com.example.notesappfirebase.Firebase

import java.io.Serializable

data class Data(
    val id: String,
    val title:String,
    val note: String): Serializable