package io.toro.ojtbe.jimenez.dictionary;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {
    private final DictionaryEntryRepository repository;

    public Controller(DictionaryEntryRepository repository){
        this.repository = repository;
    }

    @GetMapping("/dictionary")
    List<DictionaryEntry> all(){
        return repository.findAll();
    }

    @GetMapping("/dictionary/{term}")
    DictionaryEntry getOne(@PathVariable String term){
        Optional<DictionaryEntry> matchedEntry = repository.findByTermIgnoreCase(term);
        if(matchedEntry.isPresent()){
            return matchedEntry.get();
        }
        else{
            // to replace
            throw new RuntimeException();
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
        }
        else{
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
