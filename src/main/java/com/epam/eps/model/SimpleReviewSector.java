package com.epam.eps.model;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.support.randomsector.RandomSector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleReviewSector implements ReviewSector {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final double PROBABILITY = .5d;

    @RandomSector(width = WIDTH, height = HEIGHT, fillFactor = PROBABILITY)
    private Cell[][] cells;

    @Override
    public Cell[][] getSector() {
        return cells;
    }

    @Override
    public void setSector(Cell[][] field) {
        cells = field;
    }

    @Override
    public int getWidth() {
        return cells[0].length;
    }

    @Override
    public int getHeight() {
        return cells.length;
    }

    //cell in field
    private boolean isCellInField(int x, int y) {
        return x > -1 && y > -1 &&
                x < getWidth() && y < getHeight();
    }

    @Override
    public Set<Cell> getNeighbors(Cell cell) {
        Set<Cell> neighboringCells = new HashSet<>();

        int x = cell.getX();
        int y = cell.getY();

        if (isCellInField(x - 1, y)) {
            if (cells[y][x - 1] != null) {
                neighboringCells.add(cells[y][x - 1]);
            }
        }

        if (isCellInField(x + 1, y)) {
            if (cells[y][x + 1] != null) {
                neighboringCells.add(cells[y][x + 1]);
            }
        }

        if (isCellInField(x, y - 1)) {
            if (cells[y - 1][x] != null) {
                neighboringCells.add(cells[y - 1][x]);
            }
        }

        if (isCellInField(x, y + 1)) {
            if (cells[y + 1][x] != null) {
                neighboringCells.add(cells[y + 1][x]);
            }
        }

        return neighboringCells;
    }

    @Override
    public Set<Cell> getOccupied() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}


