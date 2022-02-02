package com.github.zackhotte.nflrushing.controllers.requests;

import lombok.Data;

@Data
public class RequestParam {
    private String name;

    public boolean hasName() {
        return name != null;
    }
}
