package com.github.zackhotte.nflrushing.controllers;

import com.github.zackhotte.nflrushing.controllers.requests.RequestParam;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rushers")
public class RushersController {

    @Autowired
    private RusherRepository rusherRepository;

    @GetMapping
    public Page<Rusher> getRushers(RequestParam params, Pageable pageable) {
        if (params.hasName()) {
            var name = params.getName().toLowerCase();
            return rusherRepository.findAllByPlayerName(name, pageable);
        }

        return rusherRepository.findAll(pageable);
    }
}
