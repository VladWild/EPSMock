package com.epam.eps.model.algorithm.searchwidth;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.algorithm.Search;

import java.util.function.Predicate;

public abstract class Width extends Search {

    protected Width(Object bean) {
        super(bean);
    }

    protected abstract Group getGroupByCell(Cell cell);

    @Override
    public Group[] getGroups() {
        reviewSector.getOccupied().stream()
                .filter(((Predicate<Cell>) super::isCellInGroups).negate())
                .forEach(cell -> groups.add(getGroupByCell(cell)));

        return groups.toArray(new Group[groups.size()]);
    }
}


