package com.amaya.ciscotips.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import com.amaya.ciscotips.*

import com.amaya.ciscotips.adapters.TopicAdapter
import com.amaya.ciscotips.models.Topic

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class TopicActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        val idChapter = intent.extras.getString("idChapter")
        val title = intent.extras.getString("title")
        setTitle(title)

        recyclerView = findViewById(R.id.topicView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        if ((application as App).isOnline()) {
            launch(UI) {
                val topics = async (CommonPool) {
                    loadTopicById(idChapter)
                }.await()
                recyclerView.adapter = TopicAdapter(topics) { topic ->
                    onTopicClick(topic)
                }
            }
        }
        else {
            recyclerView.adapter = TopicAdapter(loadTopicByIdFromDb(idChapter)) { topic ->
                onTopicClick(topic)
            }
        }
    }

    private fun onTopicClick(topic: Topic) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("link", topic.link)
        intent.putExtra("title", topic.nameTopic)
        startActivity(intent)
    }
}
