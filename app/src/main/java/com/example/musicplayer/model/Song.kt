package com.example.musicplayer.model

data class Song(
    val id: String,
    val name: String,
    val artistName: String,
    val artistImage: String,
    val trackUrl: String,
    val image: String
    ){
    constructor() : this("", "", "", "", "", "")
}