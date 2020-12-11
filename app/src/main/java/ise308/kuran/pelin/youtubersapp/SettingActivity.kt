package ise308.kuran.pelin.youtubersapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

private const val TAG = "SettingsActivity"

class SettingActivity : AppCompatActivity() {
    private var showDividers: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)
        val switch_one = findViewById<Switch>(R.id.switch1)
        switch_one.isChecked = showDividers
        switch_one.setOnCheckedChangeListener { button, isChecked ->
            this.showDividers = isChecked

        }
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("dividers", showDividers)
        editor.apply()

    }
}