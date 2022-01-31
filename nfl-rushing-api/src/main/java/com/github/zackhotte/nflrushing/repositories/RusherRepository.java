package com.github.zackhotte.nflrushing.repositories;

import com.github.zackhotte.nflrushing.models.Rusher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RusherRepository extends PagingAndSortingRepository<Rusher, Long> {
    @Query(value = "SELECT * FROM rusher WHERE LOWER(player) LIKE %:name%", nativeQuery = true)
    Page<Rusher> findAllByPlayerName(@Param("name") String name, Pageable pageable);
}
