package ise308.kuran.pelin.youtubersapp
//Sorry for the package directive, it was too late for me to fix it after I noticed it.
// However my application's name is still NoteToPelinKuran.
// Thank you for your understanding.
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ise308.kuran.pelin.youtubersapp.Note
import ise308.kuran.pelin.youtubersapp.R

class DialogShowNote : DialogFragment() {

    private var note: Note? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.activity!!)
        val inflater = activity!!.layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_show_note, null)

        val textViewTitle = dialogLayout.findViewById<TextView>(R.id.textView_title)
        val textViewDescription = dialogLayout.findViewById<TextView>(R.id.textView_description)
        val textViewIdea = dialogLayout.findViewById<TextView>(R.id.textView_idea)
        val textViewTodo = dialogLayout.findViewById<TextView>(R.id.textView_todo)
        val textViewImportant = dialogLayout.findViewById<TextView>(R.id.textView_important)
        val buttonOK = dialogLayout.findViewById<Button>(R.id.button_done)
        textViewTitle.text = note!!.title
        textViewDescription.text =
            note!!.description //there was a bug here, I see that some of my layoutIDs were wrong, however stackoverflow was there for me to help! :)

        if (!note!!.important) {
            textViewImportant.visibility = View.GONE
        }
        if (!note!!.todo) {
            textViewTodo.visibility = View.GONE
        }
        if (!note!!.idea) {
            textViewIdea.visibility = View.GONE
        }
        buttonOK.setOnClickListener {
            dismiss()
        }
        builder.setView(dialogLayout)
            .setMessage("Your Note")
        return builder.create()


    }

    fun sendNoteSelected(noteselected: Note) {

        note = noteselected
    }
}