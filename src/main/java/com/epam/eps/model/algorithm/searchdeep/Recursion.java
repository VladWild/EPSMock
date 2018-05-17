package com.epam.eps.model.algorithm.searchdeep;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.Set;

public class Recursion extends Deep {

    public Recursion(Object bean) {
        super(bean);
    }

    @Override
    protected Group getGroupByCell(Set<Cell> cellsGroup, Cell cell) {
        Set<Cell> neighborsCells = reviewSector.getNeighbors(cell);
        if (!neighborsCells.isEmpty()){
            for (Cell neighborsCell : neighborsCells){
                if (!cellsGroup.contains(neighborsCell)) {
                    cellsGroup.add(neighborsCell);
                    getGroupByCell(cellsGroup, neighborsCell);
                }
            }
        }
        if (cellsGroup.isEmpty()) cellsGroup.add(cell);
        return getGroup(cellsGroup);
    }
}

