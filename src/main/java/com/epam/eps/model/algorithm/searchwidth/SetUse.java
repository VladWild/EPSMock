package com.epam.eps.model.algorithm.searchwidth;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.HashSet;
import java.util.Set;

public class SetUse extends Width {

    public SetUse(Object bean) {
        super(bean);
    }

    @Override
    protected Group getGroupByCell(Cell cell){
        Set<Cell> cellsGroup = new HashSet<>();

        cellsGroup.add(cell);
        if (reviewSector.getNeighbors(cell).isEmpty()) return getGroup(cellsGroup);

        Set<Cell> groupsCurrentLevel = new HashSet<>();
        groupsCurrentLevel.addAll(reviewSector.getNeighbors(cell));

        Set<Cell> groupsNextLevel = new HashSet<>();

        while (!cellsGroup.containsAll(groupsCurrentLevel)){
            cellsGroup.addAll(groupsCurrentLevel);
            groupsCurrentLevel.forEach(cellCurrentLevel ->
                    groupsNextLevel.addAll(reviewSector.getNeighbors(cellCurrentLevel)));
            groupsCurrentLevel.clear();
            groupsCurrentLevel.addAll(groupsNextLevel);
            groupsNextLevel.clear();
        }

        return getGroup(cellsGroup);
    }
}
