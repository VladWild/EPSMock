package com.epam.eps.model.algorithm;

import com.epam.eps.EmergencyPreventionSystem;
import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.ReviewSector;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Search implements Algorithm {
    protected ReviewSector reviewSector;
    protected List<Group> groups = new ArrayList<>();

    protected Search(Object bean) {
        reviewSector = (ReviewSector) bean;
    }

    protected Group getGroup(Set<Cell> cellsGroup) {
        Group group = new Group();
        group.setCells(cellsGroup);
        return group;
    }

    public boolean isCellInGroups(Cell cell) {
        return groups.stream()
                .map(Group::getCells)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .contains(cell);
    }
}
