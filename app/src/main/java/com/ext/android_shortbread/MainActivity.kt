package com.ext.android_shortbread

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ext.short_bread.ShortcutHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup launcher shortcuts
        setupLauncherShortcuts()

        // Handle shortcut clicks if app is opened via shortcut
        handleShortcut(intent)
    }

    private fun setupLauncherShortcuts() {
        ShortcutHelper(this)
            .setTargetActivity(MainActivity::class.java)
            .setIconShape(ShortcutHelper.IconShape.CIRCLE)
            .setIconBackgroundColor(Color.parseColor("#FFFFFF"))

            // Add shortcuts (only launcher-supported customizations)
            .addShortcut(
                id = "add_movie",
                shortLabel = "Add movie",
                longLabel = "Add a new movie",
                iconRes = R.drawable.ic_add_movie,
                iconTintColor = Color.parseColor("#B71C1C"), // Dark red icon
                iconBackgroundColor = Color.parseColor("#FFFFFF") // White circle
            )
            .addShortcut(
                id = "movies",
                shortLabel = "Movies",
                longLabel = "Open Movies List",
                iconRes = R.drawable.ic_movies,
                iconTintColor = Color.parseColor("#B71C1C"),
                iconBackgroundColor = Color.parseColor("#FFFFFF")
            )
            .addShortcut(
                id = "favorite_books",
                shortLabel = "Favorite books",
                longLabel = "Open Favorite Books",
                iconRes = R.drawable.ic_favorite_books,
                iconTintColor = Color.parseColor("#B71C1C"),
                iconBackgroundColor = Color.parseColor("#FFFFFF")
            )
            .addShortcut(
                id = "books",
                shortLabel = "Books",
                longLabel = "Open Books Library",
                iconRes = R.drawable.ic_books,
                iconTintColor = Color.parseColor("#B71C1C"), // White icon
                iconBackgroundColor = Color.parseColor("#FFFFFF") // Black circle
            )
            .setupShortcuts()
    }

    private fun handleShortcut(intent: Intent?) {
        intent ?: return
        val shortcutId = intent.getStringExtra("shortcut_id") ?: return

        val message = when (shortcutId) {
            "add_movie" -> {
                openPage("Add Movie")
                "📽️ Opening Add Movie..."
            }
            "movies" -> {
                openPage("Movies List")
                "🎬 Opening Movies..."
            }
            "favorite_books" -> {
                openPage("Favorite Books")
                "⭐ Opening Favorite Books..."
            }
            "books" -> {
                openPage("Books Library")
                "📚 Opening Books Library..."
            }
            else -> "Unknown shortcut"
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openPage(pageTitle: String) {
        startActivity(
            Intent(this, PageActivity::class.java).apply {
                putExtra("PAGE_TITLE", pageTitle)
            }
        )
    }
}
