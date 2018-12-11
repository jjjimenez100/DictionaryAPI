package io.toro.ojtbe.jimenez.dictionary.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DictionaryEntry {
    private @Id @GeneratedValue Long id;
    @Column(length = 255)
    private String term;
    @Column(columnDefinition = "Text")
    private String definition;

    public DictionaryEntry(String term, String definition){
        this.term = term;
        this.definition = definition;
    }

    private DictionaryEntry() {}
}
