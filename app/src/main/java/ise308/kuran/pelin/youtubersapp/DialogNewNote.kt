package ise308.kuran.pelin.notetopelinkuran

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ise308.kuran.pelin.youtubersapp.MainActivity
import ise308.kuran.pelin.youtubersapp.Note
import ise308.kuran.pelin.youtubersapp.R

//Dialogs, arrays of objects,
class DialogNewNote : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.activity!!)

        val inflater = activity!!.layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_new_note, null)
        val editTitle = dialogLayout.findViewById<EditText>(R.id.edit_title)
        val editDescription = dialogLayout.findViewById<EditText>(R.id.edit_description)
        val checkBoxIdea = dialogLayout.findViewById<CheckBox>(R.id.checkBox_idea)
        val checkBoxTodo = dialogLayout.findViewById<CheckBox>(R.id.checkBox_todo)
        val checkBoImportant = dialogLayout.findViewById<CheckBox>(R.id.checkBox_important)
        val buttonOK = dialogLayout.findViewById<Button>(R.id.button_ok)
        val buttonCancel = dialogLayout.findViewById<Button>(R.id.button_cancel)
        builder.setView(dialogLayout)
            .setMessage("Add a new note")
        buttonCancel.setOnClickListener {
            dismiss()

        }
        buttonOK.setOnClickListener {
            val newNote = Note()
            newNote.title = editTitle.text.toString()
            newNote.description = editDescription.text.toString()
            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxTodo.isChecked
            newNote.important = checkBoImportant.isChecked
            val callingActivity = activity as MainActivity?
            callingActivity!!.createNewNote(newNote)
            dismiss()

        }
        return builder.create()
    }
}