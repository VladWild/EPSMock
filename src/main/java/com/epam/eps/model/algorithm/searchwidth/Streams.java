package com.epam.eps.model.algorithm.searchwidth;

import com.epam.eps.EmergencyPreventionSystem;
import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.algorithm.Search;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams extends Search {
    private final static Logger logger = Logger.getLogger(Streams.class);

    public Streams(Object bean) {
        super(bean);
    }

    private Group getGroupByCell(Cell cell) {
        Set<Cell> group = new HashSet<>();
        group.add(cell);

        Set<Cell> buffer = new HashSet<>(group);

        while (!buffer.isEmpty()) {
            buffer = buffer.stream().map(currentCell -> reviewSector.getNeighbors(currentCell))
                    .flatMap(Collection::stream)
                    .filter(currentCell -> !group.contains(currentCell))
                    .collect(Collectors.toSet());
            group.addAll(buffer);
        }

        return getGroup(group);
    }

    @Override
    public Group[] getGroups() {
        reviewSector.getOccupied().stream()
                .filter(((Predicate<Cell>) super::isCellInGroups).negate())
                .forEach(cell -> groups.add(getGroupByCell(cell)));

        return groups.toArray(new Group[groups.size()]);
    }
}
