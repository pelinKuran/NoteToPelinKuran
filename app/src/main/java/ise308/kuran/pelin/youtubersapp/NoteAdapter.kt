package ise308.kuran.pelin.youtubersapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val noteList: ArrayList<Note>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<NoteAdapter.ItemHolder>() {
    inner class ItemHolder(ItemView: View) :
        RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        internal var title_of_note =
            ItemView.findViewById<TextView>(R.id.textView_item_name) as TextView
        internal var description_of_note =
            ItemView.findViewById<TextView>(R.id.textView_item_description) as TextView
        internal var status_of_note =
            ItemView.findViewById<TextView>(R.id.textView_item_status) as TextView

        init { //make it clickable
            ItemView.isClickable = true
            ItemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mainActivity.showNote(adapterPosition)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemInflater =
            LayoutInflater.from(parent.context) //creates new layout by inflating.
        val ItemView =
            itemInflater.inflate(R.layout.list_item, parent, false) //root layout
        return ItemHolder(ItemView)
    }

    override fun getItemCount() = noteList.size //adapter should know size.
    override fun onBindViewHolder(
        holder: ItemHolder,
        position: Int
    ) { // everytime you list data find appropriate data.
        var note = noteList[position]
        holder.title_of_note.text = note.title
        val substringLength = if (note.description!!.length < 15) note.description!!.length else 20
        val preview_desc="${note.description!!.substring(0,substringLength)}"
            holder.description_of_note.text = preview_desc
        holder.status_of_note.text = note.status
        when{
            note.idea ->holder.status_of_note.text="Idea"
            note.important->holder.status_of_note.text="Important"
            note.todo->holder.status_of_note.text="ToDo"
        }

        if (position == 0) holder.title_of_note.setBackgroundColor(Color.LTGRAY)
    }

}
