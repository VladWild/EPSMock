package com.epam.eps.model.algorithm.searchwidth;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.algorithm.Search;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Cycles extends Search {
    private final static Logger logger = Logger.getLogger(Cycles.class);

    public Cycles(Object bean) {
        super(bean);
    }

    private Group getGroupByCell(Cell cell) {
        logger.trace("Create group");
        Set<Cell> group = new HashSet<>();
        group.add(cell);

        logger.trace("Create buffer");
        Set<Cell> buffer = new HashSet<>(group);

        while (!buffer.isEmpty()) {
            logger.trace("Create collection neighbor cells");
            Set<Cell> allNeighbors = new HashSet<>();
            logger.trace("Add neighbor cells");
            for (Cell currentCell : buffer) {
                allNeighbors.addAll(reviewSector.getNeighbors(currentCell));
            }
            logger.trace("Add neighbor cells in buffer");
            buffer.addAll(allNeighbors);
            logger.trace("Removal of neighboring cells that already exist in the group");
            buffer.removeAll(group);
            logger.trace("Add new cells in the group");
            group.addAll(buffer);
        }

        return getGroup(group);
    }

    @Override
    public Group[] getGroups() {
        logger.trace("Get all occupied cells in field");
        Set<Cell> occupiedCells = reviewSector.getOccupied();
        for (Cell cell : occupiedCells) {
            if (!isCellInGroups(cell)) {
                logger.trace("Get group by cell - " + cell);
                groups.add(getGroupByCell(cell));
            }
        }

        return groups.toArray(new Group[groups.size()]);
    }
}
