package com.github.zackhotte.nflrushing.controllers;

import com.github.zackhotte.nflrushing.controllers.requests.RequestParam;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.services.RushingService;

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
    private RushingService service;

    @GetMapping
    public Page<Rusher> getRushers(RequestParam params, Pageable pageable) {
        return service.getAll(params, pageable);
    }
}
