package com.example.mysqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root) // Use binding.root instead of R.layout.

        db = NoteDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1) // Fix the typo here, replace "note_id, -1" with "note_id", -1
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.updatetittleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)

        binding.updatesaveButton.setOnClickListener {
            val newTitle = binding.updatetittleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show() // Fix the typo here, replace "changes saved" with "Changes saved"
        }
    }
}
