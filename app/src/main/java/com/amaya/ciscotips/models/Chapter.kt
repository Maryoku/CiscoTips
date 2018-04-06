package com.amaya.ciscotips.models

data class Chapter (
        val idChapter: String,
        val nameChapter: String,
        val descriptionChapter: String,
        val imageUrl: String
) {
    class List : ArrayList<Chapter>()
}