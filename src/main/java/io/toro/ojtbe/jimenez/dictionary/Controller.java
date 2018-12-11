package io.toro.ojtbe.jimenez.dictionary;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
        return repository.findAll().stream().filter(entry -> entry.getTerm().equalsIgnoreCase(term)).findFirst().orElse(null);
    }
}
