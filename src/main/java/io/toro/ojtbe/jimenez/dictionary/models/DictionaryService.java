package io.toro.ojtbe.jimenez.dictionary.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DictionaryService {
    Page<DictionaryEntry> getAllEntries(Pageable page);

    Optional<DictionaryEntry> getEntryByTerm(String term);

    boolean isEntryExisting(String term);

    long getTotalNumberOfEntries();

    DictionaryEntry saveNewEntry(DictionaryEntry newEntry);

    void deleteEntryByTerm(String term);
}
