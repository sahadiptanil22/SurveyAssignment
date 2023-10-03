package com.diptanil.surveyassignment.data.model


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Option(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("display_text")
    val displayText: String,
    val value: String
)