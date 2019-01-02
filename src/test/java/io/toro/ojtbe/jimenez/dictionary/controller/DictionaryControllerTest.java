package io.toro.ojtbe.jimenez.dictionary.controller;

import io.toro.ojtbe.jimenez.dictionary.controllers.DictionaryController;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(DictionaryController.class)
public class DictionaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenEntries_whenGetAllEntries_thenReturnEntriesInJson() throws Exception{
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

        List<DictionaryEntry> entries = Arrays.asList(entry, secondEntry, thirdEntry);

        mockMvc.perform(get("/api/v1/dictionary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].term", is(entry.getTerm())));
    }
}
