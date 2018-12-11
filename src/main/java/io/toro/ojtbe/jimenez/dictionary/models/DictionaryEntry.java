package io.toro.ojtbe.jimenez.dictionary.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class DictionaryEntry {
    private @Id @GeneratedValue Long id;

    @NotNull
    @Column(length = 255)
    private String term;

    @NotNull
    @Column(columnDefinition = "Text")
    private String definition;

    public DictionaryEntry(String term, String definition){
        this.term = term;
        this.definition = definition;
    }

    private DictionaryEntry() {}
}
