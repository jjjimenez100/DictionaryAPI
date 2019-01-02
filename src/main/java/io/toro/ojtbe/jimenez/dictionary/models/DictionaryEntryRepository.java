package io.toro.ojtbe.jimenez.dictionary.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DictionaryEntryRepository extends JpaRepository<DictionaryEntry, Long> {
    Optional<DictionaryEntry> findByTermIgnoreCase(String term);
    boolean existsByTerm(String term);
    void deleteByTerm(String term);
}

