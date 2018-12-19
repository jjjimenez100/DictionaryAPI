package io.toro.ojtbe.jimenez.dictionary.controllers;

import io.toro.ojtbe.jimenez.dictionary.error.ResourceNotFound;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
final class DictionaryController {
    private final DictionaryEntryRepository repository;

    public DictionaryController(DictionaryEntryRepository repository){
        this.repository = repository;
    }

    @GetMapping("/dictionary")
    List<DictionaryEntry> all(){
        return repository.findAll();
    }

    @GetMapping("/dictionary/{term}")
    ResponseEntity getOne(@PathVariable String term){
        Optional<DictionaryEntry> matchedEntry = repository.findByTermIgnoreCase(term);
        if(matchedEntry.isPresent()){
            return new ResponseEntity(matchedEntry.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(new ResourceNotFound("RESOURCE_NOT_FOUND", term + " does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/dictionary")
    DictionaryEntry addNewEntry(@RequestBody @Valid DictionaryEntry newEntry){
        return repository.save(newEntry);
    }

    @PutMapping("/dictionary/{term}")
    DictionaryEntry modifyEntry(@RequestBody DictionaryEntry newEntry, @PathVariable String term){
        Optional<DictionaryEntry> matchedEntry = repository.findByTermIgnoreCase(term);
        if(matchedEntry.isPresent()){
            DictionaryEntry entry = matchedEntry.get();
            if(!newEntry.getTerm().isEmpty()){
                entry.setTerm(newEntry.getTerm());
            }
            if(!newEntry.getDefinition().isEmpty()){
                entry.setDefinition(newEntry.getDefinition());
            }
            return repository.save(entry);
        } else {
            newEntry.setTerm(term);
            return repository.save(newEntry);
        }
    }

    @DeleteMapping("/dictionary/{term}")
    void deleteEntry(@PathVariable String term) {
        Optional<DictionaryEntry> matchedEntry = repository.findByTermIgnoreCase(term);
        if(matchedEntry.isPresent()){
            repository.delete(matchedEntry.get());
        }
    }
}
