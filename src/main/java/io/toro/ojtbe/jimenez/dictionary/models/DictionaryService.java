package io.toro.ojtbe.jimenez.dictionary.models;

import java.util.List;
import java.util.Optional;

public interface DictionaryService {
    List<DictionaryEntry> getAllEntries();
    Optional<DictionaryEntry> getEntryByTerm(String term);

    boolean isEntryExisting(String term);

    long getTotalNumberOfEntries();

    DictionaryEntry saveNewEntry(DictionaryEntry newEntry);

    void deleteEntryByTerm(String term);
}
