package com.epam.eps.model.algorithm;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Optimal2 extends Search {

    public Optimal2(Object bean) {
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
        reviewSector.getOccupied().stream().filter(((Predicate<Cell>) super::isCellInGroups).negate()).forEach(cell -> groups.add(getGroupByCell(cell)));

        return groups.stream().toArray(Group[]::new);
    }
}
