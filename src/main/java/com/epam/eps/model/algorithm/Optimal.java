package com.epam.eps.model.algorithm;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

class Optimal extends Search {
    private Cell currentCell;
    private boolean flag = false;

    public Optimal(Object bean) {
        super(bean);
    }

    private Group getGroupByCell(Set<Cell> cellsGroup, Cell cell) {
        if (currentCell.equals(cell) && flag) return getGroup(cellsGroup);
        if (!flag) flag = true;
        Set<Cell> neighborsCells = reviewSector.getNeighbors(cell);
        if (!neighborsCells.isEmpty()) {
            for (Cell neighborsCell : neighborsCells) {
                if (!cellsGroup.contains(neighborsCell)) {
                    cellsGroup.add(neighborsCell);
                    getGroupByCell(cellsGroup, neighborsCell);
                }
            }
        }
        if (cellsGroup.isEmpty()) cellsGroup.add(cell);
        return getGroup(cellsGroup);
    }

    @Override
    public Group[] getGroups() {
        reviewSector.getOccupied().stream()
                .filter(((Predicate<Cell>) super::isCellInGroups).negate())
                .forEach(cell -> {
                    currentCell = cell;
                    flag = false;
                    groups.add(getGroupByCell(new HashSet<>(), cell));
                });

        return groups.toArray(new Group[groups.size()]);
    }
}




