package io.toro.ojtbe.jimenez.dictionary.controllers;

import io.toro.ojtbe.jimenez.dictionary.controllers.DictionaryController;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class DictionaryEntryResourceAssembler implements ResourceAssembler<DictionaryEntry, Resource<DictionaryEntry>> {

    @Override
    public Resource<DictionaryEntry> toResource(DictionaryEntry entry){
        return new Resource<>(entry,
                linkTo(methodOn(DictionaryController.class).getOne(null, entry.getTerm())).withSelfRel(),
                linkTo(methodOn(DictionaryController.class).all(null)).withRel("entries"));
    }
}
