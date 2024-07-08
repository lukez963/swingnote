package com.tool;

import org.springframework.data.repository.CrudRepository;

public interface NoteDB extends CrudRepository<NoteData, Integer> {
    NoteData findById(int id);
}