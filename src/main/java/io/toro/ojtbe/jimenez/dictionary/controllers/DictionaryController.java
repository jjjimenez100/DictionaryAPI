package io.toro.ojtbe.jimenez.dictionary.controllers;

import io.toro.ojtbe.jimenez.dictionary.error.ResourceNotFound;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/dictionary")
public final class DictionaryController {

    @Autowired
    private final DictionaryService service;

    @Autowired
    private final DictionaryEntryResourceAssembler assembler;

    private final Logger logger;

    public DictionaryController(DictionaryService service, DictionaryEntryResourceAssembler assembler){
        this.service = service;
        this.assembler = assembler;
        this.logger = LoggerFactory.getLogger(DictionaryController.class);
    }

    @GetMapping("/entries")
    List<DictionaryEntry> all(HttpServletRequest request){
        logRequestDetails(request);
        return service.getAllEntries();
    }

    @GetMapping("/entries/{term}")
    ResponseEntity getOne(HttpServletRequest request, @PathVariable String term){
        logRequestDetails(request);
        Optional<DictionaryEntry> matchedEntry = service.getEntryByTerm(term);
        if(matchedEntry.isPresent()){
            return new ResponseEntity<>(matchedEntry.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ResourceNotFound("RESOURCE_NOT_FOUND", term + " does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/entries")
    DictionaryEntry addNewEntry(HttpServletRequest request, @RequestBody @Valid DictionaryEntry newEntry){
        logRequestDetails(request);
        return service.saveNewEntry(newEntry);
    }

    @PutMapping("/entries/{term}")
    DictionaryEntry modifyEntry(HttpServletRequest request, @RequestBody DictionaryEntry newEntry, @PathVariable String term){
        logRequestDetails(request);
        Optional<DictionaryEntry> matchedEntry = service.getEntryByTerm(term);
        if(matchedEntry.isPresent()){
            DictionaryEntry entry = matchedEntry.get();
            if(!newEntry.getTerm().isEmpty()){
                entry.setTerm(newEntry.getTerm());
            }
            if(!newEntry.getDefinition().isEmpty()){
                entry.setDefinition(newEntry.getDefinition());
            }
            return service.saveNewEntry(entry);
        } else {
            newEntry.setTerm(term);
            return service.saveNewEntry(newEntry);
        }
    }

    @DeleteMapping("/entries/{term}")
    void deleteEntry(HttpServletRequest request, @PathVariable String term) {
        logRequestDetails(request);
        service.deleteEntryByTerm(term);
    }

    private void logRequestDetails(HttpServletRequest request){
        logger.info("URI: " + request.getRequestURI());
        logger.info("Method: " + request.getMethod());
        logger.info("Queries: "+ request.getQueryString());
        logger.info("Content-Type: " + request.getContentType());
        logger.info("Remote Ip Addr: " + request.getRemoteAddr());
    }
}
