package com.tool;

import org.springframework.stereotype.Component;

@Component
public class IDGenerator {
    private int id;

    IDGenerator() {
        id = 0;
    }

    public int getID() {
        int oldid = id;
        id++;
        return oldid;
    }
}