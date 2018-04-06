package com.amaya.ciscotips.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.content.Intent
import android.os.Bundle

import com.amaya.ciscotips.adapters.ChapterAdapter
import com.amaya.ciscotips.loadChaptersFromDb
import com.amaya.ciscotips.models.Chapter
import com.amaya.ciscotips.loadChapters
import com.amaya.ciscotips.App
import com.amaya.ciscotips.R

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.chapterView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        if ((application as App).isOnline()) {
            launch(UI) {
                val chapters = async (CommonPool) {
                    loadChapters()
                }.await()
                recyclerView.adapter = ChapterAdapter(chapters) { chapter ->
                    onChapterClick(chapter)
                }
            }
        }
        else {
            recyclerView.adapter = ChapterAdapter(loadChaptersFromDb()) { chapter ->
                onChapterClick(chapter)
            }
        }
    }

    private fun onChapterClick(chapter: Chapter){
        val intent = Intent(this, TopicActivity::class.java)
        intent.putExtra("idChapter", chapter.idChapter)
        intent.putExtra("title", chapter.nameChapter)
        startActivity(intent)
    }

}
