package io.toro.ojtbe.jimenez.dictionary;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import io.toro.ojtbe.jimenez.dictionary.serialization.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DatabaseSeeder{
    private final DictionaryEntryRepository repository;

    @Autowired
    public DatabaseSeeder(DictionaryEntryRepository repository){
        this.repository = repository;
    }

    public void run(String... Strings) throws Exception {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Map<String, String> contents = JsonParser.instance.jsonToMap("https://raw.githubusercontent.com/matthewreagan/Websters" +
                "EnglishDictionary/master/dictionary_compact.json");

        int counter = 0;
        for(String key : contents.keySet()){
            System.out.println("Adding entry #" + ++counter);
            repository.save(new DictionaryEntry(key, contents.get(key)));
        }
    }
}
