package com.example.mysqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView) ?: run {
            showToast("Title TextView not found")
            TextView(itemView.context) // Creating a dummy TextView to avoid NPE
        }

        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView) ?: run {
            showToast("Content TextView not found")
            TextView(itemView.context) // Creating a dummy TextView to avoid NPE
        }

        val updateButton: ImageView = itemView.findViewById(R.id.updatesaveButton) ?: run {
            showToast("Update Button not found")
            ImageView(itemView.context) // Creating a dummy Button to avoid NPE
        }

        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton) ?: run {
            showToast("Delete Button not found")
            ImageView(itemView.context) // Creating a dummy Button to avoid NPE
        }

        private fun showToast(message: String) {
            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply{
                putExtra("note_id",note.id)

            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
