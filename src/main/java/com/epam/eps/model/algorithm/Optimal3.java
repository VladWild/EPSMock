package com.epam.eps.model.algorithm;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.HashSet;
import java.util.Set;

public class Optimal3 extends Search {

    public Optimal3(Object bean) {
        super(bean);
    }

    private Group getGroupByCell(Cell cell){
        Set<Cell> group = new HashSet<>();
        group.add(cell);

        Set<Cell> buffer = new HashSet<>(group);

        while (!buffer.isEmpty()){
            Set<Cell> allNeighbors = new HashSet<>();
            for (Cell currentCell : buffer) {
                allNeighbors.addAll(reviewSector.getNeighbors(currentCell));
            }
            buffer.addAll(allNeighbors);
            buffer.removeAll(group);
            group.addAll(buffer);
        }

        return getGroup(group);
    }

    @Override
    public Group[] getGroups() {
        Set<Cell> occupiedCells = reviewSector.getOccupied();
        for (Cell cell : occupiedCells) {
            if (!isCellInGroups(cell)) {
                groups.add(getGroupByCell(cell));
            }
        }

        return groups.toArray(new Group[groups.size()]);
    }
}
