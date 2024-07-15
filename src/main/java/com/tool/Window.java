package com.tool;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.lang.Iterable;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Window {
    public JTextArea ta;
    ArrayList<NoteButton> notes;
    private JPanel notePanel;
    private JPanel noteButtons;
    public NoteButton lastNote;
    private NoteDB db;

    public JFrame frame;

    private IDGenerator idGen;

    @Autowired
    Window(ArrayList<NoteButton> notes, NoteDB db, IDGenerator idGen) {
        this.notes = notes;
        this.db = db;
        this.idGen = idGen;
    }

    public void makeGUI() {
        frame = new JFrame("SwingNotes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add text box on the right
        ta = new JTextArea(40, 70);
        JScrollPane textScroll = new JScrollPane(ta);
        frame.add(textScroll);

        //add buttons for note-switching on the left
        notePanel = new JPanel();
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));

        noteButtons = new JPanel();
        noteButtons.setLayout(new BoxLayout(noteButtons, BoxLayout.Y_AXIS));
        JScrollPane noteScroll = new JScrollPane(noteButtons);

        //add button for adding more note buttons
        JButton addNoteButton = new JButton("Add note");
        addNoteButton.addActionListener((event) -> {
            addNewNote();
        });
        notePanel.add(noteScroll);
        notePanel.add(addNoteButton);

        frame.add(notePanel, BorderLayout.WEST);
        loadNotes();

        //saving notes before the application closes
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                saveNotes();
            }
        });

        //setting dimensions and making window visible
        frame.pack();
        frame.setVisible(true);
    }

    private void saveNotes() {
        if (lastNote != null) {
            lastNote.setContent(ta.getText());
        }
        for (NoteButton note : notes) {
            System.out.println("saving note: " + note.getTitle());
            NoteData nd = db.findById(note.getId());
            if (nd == null) {
                System.out.println("creating new entry for note: " + note.getTitle());
                db.save(new NoteData(note.getId(), note.getTitle(), note.getContent()));
            } else {
                nd.setTitle(note.getTitle());
                nd.setContent(note.getContent());
                db.save(nd);
            }
        }
    }

    private void loadNotes() {
        Iterable<NoteData> noteDatas = db.findAll();
        int[] maxID = {0};
        noteDatas.forEach(noteData -> {
            System.out.println("loading note: " + noteData.getTitle());
            if (noteData.getId() > maxID[0]) {
                maxID[0] = noteData.getId();
            }
            NoteButton button = new NoteButton(noteData.getId(), noteData.getTitle(), noteData.getContent());
            //System.out.println(noteData.getTitle());
            notes.add(button);
            noteButtons.add(button);
            button.addActionListener((event) -> {
                switchNotes(event);
            });
        });
        if (notes.size() > 0) {
            lastNote = notes.get(0);
        }
        if (lastNote != null) {
            ta.setText(lastNote.getContent());
        }
        idGen.changeStartID(maxID[0] + 1);
    }

    private void addNewNote(String name) {
        NoteButton button = new NoteButton(name, idGen);
        notes.add(button);
        noteButtons.add(button);
        button.addActionListener((event) -> {
            switchNotes(event);
        });
        if (notes.size() == 1) {
            lastNote = button;
        }
        notePanel.revalidate();
        notePanel.repaint();
    }

    private void addNewNote() {
        String name = JOptionPane.showInputDialog(frame, "Enter a name for your new note: ");
        addNewNote(name);
    }

    private void switchNotes(ActionEvent a) {
        lastNote.setContent(ta.getText());
        NoteButton button = (NoteButton) a.getSource();
        lastNote = button;
        ta.setText(button.getContent());
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                makeGUI();
            }
        });
    }
}