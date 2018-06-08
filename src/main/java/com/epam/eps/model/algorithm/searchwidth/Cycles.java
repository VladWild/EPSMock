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
        logger.debug("Create group");
        Set<Cell> group = new HashSet<>();
        group.add(cell);

        logger.debug("Create buffer");
        Set<Cell> buffer = new HashSet<>(group);

        while (!buffer.isEmpty()) {
            logger.debug("Create collection neighbor cells");
            Set<Cell> allNeighbors = new HashSet<>();
            logger.debug("Add neighbor cells");
            for (Cell currentCell : buffer) {
                allNeighbors.addAll(reviewSector.getNeighbors(currentCell));
            }
            logger.debug("Add neighbor cells in buffer");
            buffer.addAll(allNeighbors);
            logger.debug("Removal of neighboring cells that already exist in the group");
            buffer.removeAll(group);
            logger.debug("Add new cells in the group");
            group.addAll(buffer);
        }

        return getGroup(group);
    }

    @Override
    public Group[] getGroups() {
        logger.debug("Get all occupied cells in field");
        Set<Cell> occupiedCells = reviewSector.getOccupied();
        for (Cell cell : occupiedCells) {
            if (!isCellInGroups(cell)) {
                logger.debug("Get group by cell - " + cell);
                groups.add(getGroupByCell(cell));
            }
        }

        return groups.toArray(new Group[groups.size()]);
    }
}
