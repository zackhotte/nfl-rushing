package com.github.zackhotte.nflrushing.services;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.github.zackhotte.nflrushing.controllers.requests.RequestParam;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;
import com.github.zackhotte.nflrushing.utilities.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RushingService {

    private static final String CUSTOM_SORTING_FIELD = "longestRun";

    @Autowired
    private RusherRepository rusherRepository;

    public Page<Rusher> getAll(RequestParam params, Pageable pageable) {
        if (params.hasName()) {
            var name = params.getName().toLowerCase();
            return getAll(name, pageable);
        }

        return getAll(pageable);
    }

    public Page<Rusher> getAll(Pageable pageable) {
        var rushers = rusherRepository.findAll(pageable);

        return process(rushers, pageable);
    }

    public Page<Rusher> getAll(String playerName, Pageable pageable) {
        var rushers = rusherRepository.findAllByPlayerName(playerName.toLowerCase(), pageable);

        return process(rushers, pageable);
    }

    private Page<Rusher> process(Page<Rusher> rushers, Pageable pageable) {
        if (hasSortingField(pageable, CUSTOM_SORTING_FIELD)) {
            return new PageImpl<>(
                    rushers.getContent().stream().sorted(longestRunCustomSorter()).collect(Collectors.toList()),
                    pageable,
                    rushers.getTotalElements()
            );
        }

        return rushers;
    }

    private boolean hasSortingField(Pageable pageable, String fieldName) {
        var sort = pageable.getSort();
        for (var order : sort) {
            var prop = order.getProperty();

            if (prop.equalsIgnoreCase(fieldName)) {
                return true;
            }
        }

        return false;
    }

    private Comparator<Rusher> longestRunCustomSorter() {
        return (r1, r2) -> {
            var lng1 = r1.getLongestRun();
            var lng2 = r2.getLongestRun();

            lng1 = Utils.removeSuffix(lng1, 'T');
            lng2 = Utils.removeSuffix(lng2, 'T');

            var val1 = Integer.parseInt(lng1);
            var val2 = Integer.parseInt(lng2);

            return Integer.compare(val1, val2);
        };
    }


}
