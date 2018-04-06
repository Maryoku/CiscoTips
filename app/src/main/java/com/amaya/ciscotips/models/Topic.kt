package com.amaya.ciscotips.models

data class Topic (
        val idTopic: String,
        val nameTopic: String,
        val descriptionTopic: String,
        val link: String,
        val idChapter: String
) {
    class List : ArrayList<Topic>()
}