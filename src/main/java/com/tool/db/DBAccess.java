package com.tool.db;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tool.NoteDB;
import com.tool.Window;
import com.tool.NoteButton;
import com.tool.NoteData;

@Component
public class DBAccess {
    private NoteDB db;

    @Autowired
    private Window w;

    public void saveNotes() {
        if (w.lastNote != null) {
            w.lastNote.setContent(w.ta.getText());
        }
        for (NoteButton note : w.getNotes()) {
            System.out.println("saving note: " + note.getTitle());
            NoteData nd = db.findById(note.getID());
            if (nd == null) {
                System.out.println("creating new entry for note: " + note.getTitle());
                db.save(new NoteData(note.getTitle(), note.getContent()));
            } else {
                nd.setTitle(note.getTitle());
                nd.setContent(note.getContent());
                db.save(nd);
            }
        }
    }

    public Iterable<NoteData> loadNotes() {
        Iterable<NoteData> noteDatas = db.findAll();
        return noteDatas;
    }


}
