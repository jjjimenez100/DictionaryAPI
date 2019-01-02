package io.toro.ojtbe.jimenez.dictionary.models;

import io.toro.ojtbe.jimenez.dictionary.controllers.DictionaryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public List<DictionaryEntry> getAllEntries(){
        return repository.findAll();
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

