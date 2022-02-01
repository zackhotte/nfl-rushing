package com.github.zackhotte.nflrushing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private RusherRepository rusherRepository;

    @Value("classpath:rushing.json")
    private Resource resourceFile;

    @Override
    public void run(String... args) throws Exception {
        var rushers = getRushers(resourceFile);

        rusherRepository.saveAll(rushers);
    }

    public static JsonNode loadData(Resource resourceFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var in = resourceFile.getInputStream();
        return mapper.readTree(in);
    }

    public static List<Rusher> getRushers(Resource resourceFile) throws IOException {
        var root = DatabaseLoader.loadData(resourceFile);
        List<Rusher> rushers = new ArrayList<>();
        for (var node : root) {
            rushers.add(Rusher.fromJson(node));
        }

        return rushers;
    }
}
