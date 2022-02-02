package com.github.zackhotte.nflrushing.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.zackhotte.nflrushing.controllers.requests.RequestParam;
import com.github.zackhotte.nflrushing.models.Rusher;
import com.github.zackhotte.nflrushing.repositories.RusherRepository;
import com.github.zackhotte.nflrushing.utilities.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class RushingService {

    private static final String CUSTOM_SORTING_FIELD = "longestRun";

    @Autowired
    private RusherRepository rusherRepository;

    public Page<Rusher> getAll(RequestParam params, Pageable pageable) {
        var longestRunSortable = getSortingFieldByLongestRun(pageable);
        var hasName = params.hasName();
        if (longestRunSortable.isPresent()) {
            var rushers = hasName
                ? rusherRepository.getAllRushersByPlayerName(params.getName().toLowerCase())
                : rusherRepository.getAllRushers();

            return sortLongestRun(rushers, longestRunSortable.get(), pageable);
        }

        return hasName ? getAll(params.getName().toLowerCase(), pageable) : getAll(pageable);
    }

    public Page<Rusher> getAll(Pageable pageable) {
        return rusherRepository.findAll(pageable);
    }

    public Page<Rusher> getAll(String playerName, Pageable pageable) {
        return rusherRepository.findAllByPlayerName(playerName.toLowerCase(), pageable);
    }

    private Page<Rusher> sortLongestRun(List<Rusher> rushers, Order order, Pageable pageable) {
        var direction = order.getDirection();
        var size = pageable.getPageSize();
        var page = pageable.getPageNumber() * (long) size;
        var totalElements = rushers.size();

        var orderedRushers = rushers.stream()
            .sorted(longestRunCustomSorter(direction))
            .skip(page)
            .limit(size)
            .collect(Collectors.toList());

        return new PageImpl<>(orderedRushers, pageable, totalElements);
    }

    private Optional<Order> getSortingFieldByLongestRun(Pageable pageable) {
        var sort = pageable.getSort();
        for (var order : sort) {
            var prop = order.getProperty();

            if (prop.equalsIgnoreCase(CUSTOM_SORTING_FIELD)) {
                return Optional.of(order);
            }
        }

        return Optional.empty();
    }

    private Comparator<Rusher> longestRunCustomSorter(Direction direction) {
        return (r1, r2) -> {
            var lng1 = r1.getLongestRun();
            var lng2 = r2.getLongestRun();

            lng1 = Utils.removeSuffix(lng1, 'T');
            lng2 = Utils.removeSuffix(lng2, 'T');

            var val1 = Integer.parseInt(lng1);
            var val2 = Integer.parseInt(lng2);

            return direction == Direction.ASC
                    ? Integer.compare(val1, val2)
                    : Integer.compare(val2, val1);
        };
    }

}
