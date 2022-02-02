package com.github.zackhotte.nflrushing.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rusher", indexes = @Index(name = "player_idx", columnList = "player"))
public class Rusher {
    @Id
    @GeneratedValue
    private Long id;

    private String player;
    private String team;
    private String position;

    private int attempts;
    private double attemptsPerGame;
    private int yards;
    private double average;
    private double yardsPerGame;
    private int touchdowns;
    private String longestRun;
    private int firstDowns;
    private double firstDownPercentage;
    private int runsTwentyPlusYards;
    private int runsFourtyPlusYards;
    private int fumbles;

    public static Rusher fromJson(JsonNode node) {
        return Rusher.builder()
                .player(node.get("Player").asText())
                .team(node.get("Team").asText())
                .position(node.get("Pos").asText())
                .attempts(node.get("Att").asInt())
                .attemptsPerGame(node.get("Att/G").asInt())
                .yards(node.get("Yds").asInt())
                .average(node.get("Avg").asDouble())
                .yardsPerGame(node.get("Yds/G").asInt())
                .touchdowns(node.get("TD").asInt())
                .longestRun(node.get("Lng").asText())
                .firstDowns(node.get("1st").asInt())
                .firstDownPercentage(node.get("1st%").asDouble())
                .runsTwentyPlusYards(node.get("20+").asInt())
                .runsFourtyPlusYards(node.get("40+").asInt())
                .fumbles(node.get("FUM").asInt())
                .build();
    }
}
