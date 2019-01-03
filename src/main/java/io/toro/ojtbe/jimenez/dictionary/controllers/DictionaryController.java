package io.toro.ojtbe.jimenez.dictionary.controllers;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/dictionary")
public class DictionaryController {

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

    @GetMapping(value = "/entries")
    Resources<Resource<DictionaryEntry>> all(HttpServletRequest request, Pageable pageable, PagedResourcesAssembler pagingAssembler){
        logRequestDetails(request);
        Page<DictionaryEntry> resourceEntries = service.getAllEntries(pageable);

        return pagingAssembler.toResource(resourceEntries, assembler);
    }

    @GetMapping(value = "/entries/{term}")
    Resource<DictionaryEntry> getOne(HttpServletRequest request, @PathVariable String term){
        logRequestDetails(request);
        Optional<DictionaryEntry> matchedEntry = service.getEntryByTerm(term);
        if(matchedEntry.isPresent()){
            return assembler.toResource(matchedEntry.get());
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry does not exist.");
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
            entry.setDefinition(newEntry.getDefinition());

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
