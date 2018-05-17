package com.epam.eps.model.algorithm.searchdeep;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.*;
import java.util.stream.Collectors;

public class Cycle extends Deep {

    public Cycle(Object bean) {
        super(bean);
    }

    @Override
    protected Group getGroupByCell(Set<Cell> cellsGroup, Cell cell) {
        Stack<Queue<Cell>> stack = new Stack<>();

        cellsGroup.add(cell);
        if (reviewSector.getNeighbors(cell).isEmpty()) return getGroup(cellsGroup);

        stack.push(new ArrayDeque<>(reviewSector.getNeighbors(cell)));

        do {
            if (!stack.peek().isEmpty()) {
                if (!cellsGroup.containsAll(reviewSector.getNeighbors(stack.peek().peek()))) {
                    cellsGroup.add(stack.peek().peek());
                    stack.push(new ArrayDeque<>(reviewSector
                            .getNeighbors(stack.peek().peek()).stream()
                            .filter(currentCell -> !cellsGroup.contains(currentCell))
                            .collect(Collectors.toSet())));
                } else {
                    cellsGroup.add(stack.peek().poll());
                }
            } else {
                stack.pop();
            }
        } while (!stack.isEmpty());

        return getGroup(cellsGroup);
    }
}

