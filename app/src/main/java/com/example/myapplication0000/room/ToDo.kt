package com.example.myapplication0000.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Tododo")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var name: String,
    var description: String,
    var degree: String,
    var date: String,
    var deadline: String,
    var category: String
) : Parcelable

