package com.epam.eps.model;

import com.epam.eps.framework.core.Cell;

import java.util.Set;

public interface ReviewSector {

	Cell[][] getSector();

	void setSector(Cell[][] field);

	int getWidth();

	int getHeight();

	Set<Cell> getNeighbors(Cell cell);

	Set<Cell> getOccupied();
}
