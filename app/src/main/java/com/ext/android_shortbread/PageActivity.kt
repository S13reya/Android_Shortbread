package com.ext.android_shortbread

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

        val title = intent.getStringExtra("PAGE_TITLE") ?: "Page"

        // Set ActionBar title
        supportActionBar?.title = title

        // Set title inside page UI
        findViewById<TextView>(R.id.tvPageTitle).text = title
    }
}
