package com.epam.eps.model.algorithm.searchwidth;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.*;
import java.util.stream.Collectors;

public class MapUse extends Width {

    public MapUse(Object bean) {
        super(bean);
    }

    @Override
    protected Group getGroupByCell(Cell cell){
        Map<Cell, Set<Cell>> cellsGroup = new HashMap<>();
        Map<Cell, Set<Cell>> cellsGroupBuffer = new HashMap<>();

        cellsGroup.put(cell, reviewSector.getNeighbors(cell));
        if (cellsGroup.get(cell).isEmpty()) return getGroup(cellsGroup.keySet());

        while (!cellsGroup.keySet().containsAll(cellsGroup.values().stream()
                .flatMap(neighborCells -> neighborCells.stream()).collect(Collectors.toSet()))){
            cellsGroup.values().forEach(neighborCells ->
                    neighborCells.forEach(currentCell ->
                            cellsGroupBuffer.put(currentCell, reviewSector.getNeighbors(currentCell))));
            cellsGroup.putAll(cellsGroupBuffer);
            cellsGroupBuffer.clear();
        }

        return getGroup(cellsGroup.keySet());
    }
}
