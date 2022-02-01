package com.github.zackhotte.nflrushing.repositories;

import java.util.List;

import com.github.zackhotte.nflrushing.models.Rusher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RusherRepository extends PagingAndSortingRepository<Rusher, Long> {
    String FILTER_BY_NAME_QUERY = "SELECT * FROM rusher WHERE LOWER(player) LIKE %:name%";

    @Query(value = "SELECT * FROM rusher", nativeQuery = true)
    List<Rusher> getAllRushers();

    // Filter by player name without pagination
    @Query(value = FILTER_BY_NAME_QUERY, nativeQuery = true)
    List<Rusher> getAllRushersByPlayerName(@Param("name") String name);

    @Query(value = FILTER_BY_NAME_QUERY, nativeQuery = true)
    Page<Rusher> findAllByPlayerName(@Param("name") String name, Pageable pageable);
}
