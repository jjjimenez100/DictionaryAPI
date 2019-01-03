package io.toro.ojtbe.jimenez.dictionary.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService{

    @Autowired
    private DictionaryEntryRepository repository;

    public DictionaryServiceImpl(DictionaryEntryRepository repository){
        this.repository = repository;
    }

    @Override
    public Page<DictionaryEntry> getAllEntries(Pageable page){
        return repository.findAll(page);
    }

    @Override
    public Optional<DictionaryEntry> getEntryByTerm(String term){
        return repository.findByTermIgnoreCase(term);
    }

    @Override
    public boolean isEntryExisting(String term){
        return repository.existsByTerm(term);
    }

    @Override
    public long getTotalNumberOfEntries(){
        return repository.count();
    }

    @Override
    public DictionaryEntry saveNewEntry(DictionaryEntry newEntry){
        return repository.save(newEntry);
    }

    @Override
    public void deleteEntryByTerm(String term){
        repository.deleteByTerm(term);
    }
}

