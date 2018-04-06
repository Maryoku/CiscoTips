package com.amaya.ciscotips

import com.amaya.ciscotips.models.Chapter
import com.amaya.ciscotips.models.Topic
import com.google.gson.Gson
import io.paperdb.Paper
import okhttp3.*
import okhttp3.OkHttpClient

const val URL_CHAPTERS: String = "https://api.myjson.com/bins/9qr2r"
const val URL_TOPICS: String = "https://api.myjson.com/bins/12dp9v"

private fun getResponseText(url: String): String {
    val httpClient = OkHttpClient()
    val request = Request.Builder()
            .url(url)
            .build()
    val response = httpClient.newCall(request).execute()

    return response.body()!!.string()
}

fun loadChapters(): Chapter.List {

    val chapters = Gson().fromJson(getResponseText(URL_CHAPTERS), Chapter.List::class.java)
    Paper.book().write("chapters", chapters)

    return chapters
}

fun loadChaptersFromDb(): Chapter.List {
    try {
        return Paper.book().read("chapters")
    }
    catch (e: Exception){
        return getDefaultChapter()
    }
}

private fun getDefaultChapter(): Chapter.List {

    val defaultList: Chapter.List = Chapter.List()
    val default = Chapter(
            "1",
            "Name Chapter",
            "Description",
            "Image Url"
    )
    defaultList.add(default)

    return defaultList
}

fun loadTopicById(idChapter: String): Topic.List {

    val requestTopics: Topic.List = Topic.List()
    val foods = Gson().fromJson(getResponseText(URL_TOPICS), Topic.List::class.java)

    for (item in foods) {
        if (item.idChapter == idChapter) {
            requestTopics.add(item)
        }
    }

    Paper.book("for-topics-"+idChapter).write("topics",requestTopics)

    return requestTopics
}

fun loadTopicByIdFromDb(id: String): Topic.List {
    try {
        return Paper.book("for-topics-" + id).read("topics")
    }
    catch (e: Exception){
        return getDefaultTopic()
    }
}

private fun getDefaultTopic(): Topic.List {
    val defaultList: Topic.List = Topic.List()
    val default = Topic(
            "1",
            "Name Topic",
            "Description",
            "link",
            "1"
    )
    defaultList.add(default)

    return defaultList
}