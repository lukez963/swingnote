package com.tool;

import javax.swing.*;

//the idea is that the NoteArea is initialized when you create and click your first note
//from that point on, that same NoteArea is used to render any further notes
public class NoteArea extends JPanel {
    JTextField title;
    NoteButton button;
    //text area should have 40 rows, 70 columns
    private JTextArea content;

    public NoteArea(NoteButton button) {
        this.button = button;
        title = new JTextField(70); 
        content = new JTextArea(40, 70);
        JScrollPane scroll = new JScrollPane(content);
        title.setText(button.getTitle());
        title.addActionListener((event) -> {
            button.setText(title.getText());
        }); //can change button's title via top text area
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(scroll);
    }

    public String getText() {
        return content.getText();
    }
    public void setText(String s) {
        content.setText(s);
    }

    public void setButton(NoteButton button) {
        this.button = button;
    }

    public void switchNote(NoteButton button) {
        this.button = button;
        title.setText(button.getTitle());
        content.setText(button.getContent());
    }
}
