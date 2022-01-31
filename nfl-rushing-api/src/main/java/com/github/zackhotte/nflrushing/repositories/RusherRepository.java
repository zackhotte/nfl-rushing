package com.github.zackhotte.nflrushing.repositories;

import com.github.zackhotte.nflrushing.models.Rusher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RusherRepository extends CrudRepository<Rusher, Long> {
    @Query(value = "SELECT * FROM rusher WHERE LOWER(player) LIKE %:name%", nativeQuery = true)
    Iterable<Rusher> findAllByPlayerName(@Param("name") String name);
}
