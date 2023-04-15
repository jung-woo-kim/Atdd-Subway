package com.example.subway.line.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void equal() {
        Line line1 = new Line("2호선", "green");
        Line line2 = new Line("2호선","green");

        assertEquals(line2, line1);
    }

    @Test
    void update() {
        Line line = new Line("2호선", "green");
        line.update("1호선","orange");

        assertEquals("orange",line.getColor());
        assertEquals("1호선",line.getName());
    }
}