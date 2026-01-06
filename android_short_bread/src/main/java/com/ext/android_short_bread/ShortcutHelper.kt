package com.ext.short_bread

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Launcher-safe Shortcut Helper
 * Only icon customization (what Android launcher actually supports)
 */
class ShortcutHelper(private val context: Context) {

    private val shortcuts = mutableListOf<ShortcutInfo>()
    private var targetActivityClass: Class<*> = context.javaClass

    // Defaults (launcher supported)
    private var defaultIconTintColor: Int = 0xFFB71C1C.toInt()
    private var defaultIconBackgroundColor: Int = 0xFFFFFFFF.toInt()
    private var defaultIconShape: IconShape = IconShape.CIRCLE

    enum class IconShape {
        CIRCLE, ROUNDED_SQUARE, SQUARE
    }

    fun setTargetActivity(activityClass: Class<*>): ShortcutHelper {
        targetActivityClass = activityClass
        return this
    }

    fun setIconTintColor(color: Int): ShortcutHelper {
        defaultIconTintColor = color
        return this
    }

    fun setIconBackgroundColor(color: Int): ShortcutHelper {
        defaultIconBackgroundColor = color
        return this
    }

    fun setIconShape(shape: IconShape): ShortcutHelper {
        defaultIconShape = shape
        return this
    }

    fun addShortcut(
        id: String,
        shortLabel: String,
        longLabel: String,
        @DrawableRes iconRes: Int,
        iconTintColor: Int? = null,
        iconBackgroundColor: Int? = null,
        iconShape: IconShape? = null
    ): ShortcutHelper {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val icon = createLauncherIcon(
                iconRes = iconRes,
                iconTintColor = iconTintColor ?: defaultIconTintColor,
                iconBackgroundColor = iconBackgroundColor ?: defaultIconBackgroundColor,
                iconShape = iconShape ?: defaultIconShape
            )

            val shortcut = ShortcutInfo.Builder(context, id)
                .setShortLabel(shortLabel)
                .setLongLabel(longLabel)
                .setIcon(icon)
                .setIntent(
                    Intent(context, targetActivityClass).apply {
                        action = Intent.ACTION_VIEW
                        putExtra("shortcut_id", id)
                    }
                )
                .build()

            shortcuts.add(shortcut)
        }
        return this
    }

    private fun createLauncherIcon(
        @DrawableRes iconRes: Int,
        iconTintColor: Int,
        iconBackgroundColor: Int,
        iconShape: IconShape
    ): Icon {

        val size = 192
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = iconBackgroundColor
        }

        when (iconShape) {
            IconShape.CIRCLE -> {
                canvas.drawCircle(size / 2f, size / 2f, size / 2.1f, bgPaint)
            }
            IconShape.ROUNDED_SQUARE -> {
                val r = size * 0.25f
                canvas.drawRoundRect(
                    0f, 0f, size.toFloat(), size.toFloat(),
                    r, r, bgPaint
                )
            }
            IconShape.SQUARE -> {
                canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), bgPaint)
            }
        }

        // Icon
        val drawable = ContextCompat.getDrawable(context, iconRes)?.mutate()
        drawable?.let {
            it.colorFilter = PorterDuffColorFilter(iconTintColor, PorterDuff.Mode.SRC_IN)
            val iconSize = (size * 0.55f).toInt()
            val pad = (size - iconSize) / 2
            it.setBounds(pad, pad, pad + iconSize, pad + iconSize)
            it.draw(canvas)
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Icon.createWithAdaptiveBitmap(bitmap)
        } else {
            Icon.createWithBitmap(bitmap)
        }
    }

    fun setupShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context.getSystemService(ShortcutManager::class.java)
                ?.dynamicShortcuts = shortcuts
        }
    }

    fun removeAllShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context.getSystemService(ShortcutManager::class.java)
                ?.removeAllDynamicShortcuts()
        }
    }
}
