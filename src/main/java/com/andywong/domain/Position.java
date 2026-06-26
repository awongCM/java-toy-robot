package com.andywong.domain;

public record Position(int x, int y) {

    public Position copy() {
        return new Position(x, y);
    }
}
