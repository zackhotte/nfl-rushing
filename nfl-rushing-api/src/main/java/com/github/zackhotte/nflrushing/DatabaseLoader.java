package com.github.zackhotte.nflrushing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private RusherRepository rusherRepository;

    @Value("classpath:rushing.json")
    private Resource resourceFile;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var in = resourceFile.getInputStream();
        var root = mapper.readTree(in);

        for (var node : root) {
            var rusher = Rusher.fromJson(node);
            rusherRepository.save(rusher);
        }
    }
}
