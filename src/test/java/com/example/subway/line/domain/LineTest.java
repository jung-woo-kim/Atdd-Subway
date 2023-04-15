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
}