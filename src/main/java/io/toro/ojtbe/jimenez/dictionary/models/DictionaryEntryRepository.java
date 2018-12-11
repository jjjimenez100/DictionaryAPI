package io.toro.ojtbe.jimenez.dictionary.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DictionaryEntryRepository extends JpaRepository<DictionaryEntry, Long> {
}

