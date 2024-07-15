package com.tool;

import javax.swing.JButton;

public class NoteButton extends JButton {
    private int id;
    private String title;
    private String content;
    IDGenerator idGen;

    public NoteButton(String title, IDGenerator idGen) {
        super(title);
        this.title = title;
        this.idGen = idGen;
        id = idGen.getID();
    }
    public NoteButton(int id, String title, String content) {
        super(title);
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
}