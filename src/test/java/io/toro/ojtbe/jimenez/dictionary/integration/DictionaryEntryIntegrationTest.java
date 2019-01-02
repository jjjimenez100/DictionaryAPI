package io.toro.ojtbe.jimenez.dictionary.integration;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DictionaryEntryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DictionaryEntryRepository entryRepo;

    @Test
    public void whenFindByTerm_thenReturnEntry(){
        // Given
        DictionaryEntry entry = new DictionaryEntry();
        entry.setTerm("SSDf");
        entry.setDefinition("Haha");
        entityManager.persist(entry);
        entityManager.flush();

        // When
        Optional<DictionaryEntry> foundEntry = entryRepo.findByTermIgnoreCase(entry.getTerm());

        // Then
        Assert.assertTrue(foundEntry.isPresent());
    }

    @Test
    public void whenFindAll_thenReturnAllEntries(){
        // Given
        DictionaryEntry entry = new DictionaryEntry();
        entry.setTerm("S");
        entry.setDefinition("Haha");

        DictionaryEntry secondEntry = new DictionaryEntry();
        secondEntry.setTerm("Dada");
        secondEntry.setDefinition("Ddd");

        DictionaryEntry thirdEntry = new DictionaryEntry();
        thirdEntry.setTerm("s");
        thirdEntry.setDefinition("s");

        entityManager.persist(entry);
        entityManager.persist(secondEntry);
        entityManager.persist(thirdEntry);

        entityManager.flush();

        // When
        List<DictionaryEntry> entries = entryRepo.findAll();

        // Then
        Assert.assertTrue(entries.contains(entry));
        Assert.assertTrue(entries.contains(secondEntry));
        Assert.assertTrue(entries.contains(thirdEntry));
    }

    @Test
    public void whenFindById_thenReturnEntry(){
        // Given
        DictionaryEntry entry = new DictionaryEntry();
        entry.setTerm("Ssss");
        entry.setDefinition("Haha");

        DictionaryEntry secondEntry = new DictionaryEntry();
        secondEntry.setTerm("SFSFS");
        secondEntry.setDefinition("sfasfasfasfa");

        entityManager.persist(entry);
        entityManager.persist(secondEntry);
        entityManager.flush();

        // When
        Optional<DictionaryEntry> foundEntry = entryRepo.findById(secondEntry.getId());

        // Then
        Assert.assertTrue(foundEntry.isPresent());
        Assert.assertEquals(secondEntry.getTerm(), foundEntry.get().getTerm());
    }

    @Test
    public void whenNotFoundByTerm_thenReturnNone(){
        // Given
        DictionaryEntry entry = new DictionaryEntry();
        entry.setTerm("S");
        entry.setDefinition("Haha");

        entityManager.persist(entry);
        entityManager.flush();

        // When
        Optional<DictionaryEntry> foundEntry = entryRepo.findByTermIgnoreCase("D");

        // Then
        Assert.assertFalse(foundEntry.isPresent());
    }
}
