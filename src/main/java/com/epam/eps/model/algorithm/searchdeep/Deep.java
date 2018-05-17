package com.epam.eps.model.algorithm.searchdeep;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.algorithm.Search;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class Deep extends Search {

    protected Deep(Object bean) {
        super(bean);
    }

    abstract protected Group getGroupByCell(Set<Cell> cellsGroup, Cell cell);

    @Override
    public Group[] getGroups() {
        reviewSector.getOccupied().stream()
                .filter(((Predicate<Cell>) super::isCellInGroups).negate())
                .forEach(cell -> groups.add(getGroupByCell(new HashSet<>(), cell)));

        return groups.toArray(new Group[groups.size()]);
    }
}

