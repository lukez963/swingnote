package com.tool;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class BeanDefs {
    @Bean
    public ArrayList<NoteButton> notesBean() {
        return new ArrayList<>();
    }
}