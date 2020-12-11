package ise308.kuran.pelin.youtubersapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ise308.kuran.pelin.notetopelinkuran.DialogNewNote
import java.lang.Exception

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var noteList: ArrayList<Note>? = null
    private var jsonSerializer: JSONSerializer? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var showDividers: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabNewNote =
            findViewById<FloatingActionButton>(R.id.floatingActionButton) // give a name
        fabNewNote.setOnClickListener {
            val newNoteDialog = DialogNewNote()
            newNoteDialog.show(supportFragmentManager, "123")
        }

        jsonSerializer = JSONSerializer("NoteToSelf", applicationContext)
        try { //object added to the noteList
            noteList = jsonSerializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList() //empty note list created.
            Log.e(TAG, "Error loading notes...")
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNotes)
        adapter = noteList?.let { NoteAdapter(it, this) }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = adapter


    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)
        if (showDividers) {
            recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
        } else {
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }

    fun createNewNote(newNote: Note) {
        noteList!!.add(newNote)
        adapter!!.notifyDataSetChanged()
    }


    fun showNote(noteToShow: Int) { //define onclick listener
        val showDialog = DialogShowNote()
        noteList?.get(noteToShow)?.let { showDialog.sendNoteSelected(it) }
        showDialog.show(supportFragmentManager, "124")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val b = when (id) {
            R.id.settings -> {
                val intentToSettings = Intent(this, SettingActivity::class.java)
                startActivity(intentToSettings)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
        return b
    }

    private fun saveNotes() {
        try {
            jsonSerializer!!.save(this.noteList!!)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading notes...")
        }
    }

    override fun onPause() { //saves data when user leave the application
        super.onPause()
        saveNotes()
    }
}