# **Android Shortbread - Custom Launcher Shortcut Library**

---

Android Shortbread is a small library for creating beautiful, fully customizable launcher shortcuts in your Android app. It allows you to:
 - Add multiple shortcuts on the home screen.

 - Customize icon shapes, colors, and backgrounds.

 - Pass data (like page titles) to your activity when a shortcut is clicked.

---

## ✨ **Features**

- Dynamic Launcher Shortcuts: Add shortcuts that appear on supported Android launchers.

- Custom Icon Shape: Circle, Rounded Square, Square, or Pill.

- Custom Icon Colors: Icon tint and background color.

- Shortcut Labels: Short and long labels.





  ---

# **Preview**
---
<p align="center">
  <img src="https://github.com/user-attachments/assets/c9877e16-879c-4846-92be-13d259abcd41"
       alt="Demo GIF"
       width="200">



</p>


## ⚡ **Installation**

**Step 1:** Add JitPack repository to your root build.gradle:

```gradle
maven { url = uri("https://jitpack.io") }
```

**Step 2:** Add the dependency in your app `build.gradle` (example if hosted on JitPack):  

```gradle
dependencies {
	        implementation 'com.github.Excelsior-Technologies-Community:ReadMoreTextView:1.0.0'

}
```





## **1. Setup in Activity**
```
 class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup launcher shortcuts
        setupLauncherShortcuts()

        // Handle shortcut clicks
        handleShortcut(intent)
    }

    private fun setupLauncherShortcuts() {
        ShortcutHelper(this)
            .setTargetActivity(MainActivity::class.java)
            .setIconShape(ShortcutHelper.IconShape.CIRCLE)
            .setIconBackgroundColor(Color.WHITE)

            .addShortcut(
                id = "add_movie",
                shortLabel = "Add Movie",
                longLabel = "Add a new movie",
                iconRes = R.drawable.ic_add_movie,
                iconTintColor = Color.parseColor("#B71C1C"),
                iconBackgroundColor = Color.WHITE
            )
            .addShortcut(
                id = "movies",
                shortLabel = "Movies",
                longLabel = "Open Movies List",
                iconRes = R.drawable.ic_movies,
                iconTintColor = Color.parseColor("#B71C1C"),
                iconBackgroundColor = Color.WHITE
            )
            .setupShortcuts()
    }

    private fun handleShortcut(intent: Intent?) {
        val shortcutId = intent?.getStringExtra("shortcut_id") ?: return
        when (shortcutId) {
            "add_movie" -> openPage("Add Movie")
            "movies" -> openPage("Movies List")
        }
    }

    private fun openPage(pageTitle: String) {
        startActivity(
            Intent(this, PageActivity::class.java).apply {
                putExtra("PAGE_TITLE", pageTitle)
            }
        )
    }
}


```



## **📄 License**

**MIT License**  
```
Copyright (c) 2025 Excelsior Technologies

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED **"AS IS"**, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```



  
