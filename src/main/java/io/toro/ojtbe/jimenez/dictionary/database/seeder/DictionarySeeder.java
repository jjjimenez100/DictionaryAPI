package io.toro.ojtbe.jimenez.dictionary.database.seeder;

import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntry;
import io.toro.ojtbe.jimenez.dictionary.models.DictionaryEntryRepository;
import io.toro.ojtbe.jimenez.dictionary.serialization.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

// @Component
public final class DictionarySeeder implements CommandLineRunner{
    private final DictionaryEntryRepository repository;

    @Autowired
    public DictionarySeeder(DictionaryEntryRepository repository){
        this.repository = repository;
    }

    @Override
    public void run(String... Strings) throws Exception {
        String url = "https://raw.githubusercontent.com/matthewreagan/Websters" +
                "";
        Logger logger = LoggerFactory.getLogger(DictionarySeeder.class);
        logger.info("Requesting JSON file at: " + url);

        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Map<String, String> contents = JsonParser.instance.jsonToMap(url +
                "EnglishDictionary/master/dictionary_compact.json");
        logger.info("Request complete");
        logger.info("Starting seeding process. . .");

        logger.info("Postgresql -- Db: dictionary");
        logger.info("Adding to database");
        int counter = 0;
        for(String key : contents.keySet()){
            System.out.println("Adding entry #" + ++counter);
            repository.save(new DictionaryEntry(key, contents.get(key)));
        }
        logger.info("Seeding complete");
    }
}
