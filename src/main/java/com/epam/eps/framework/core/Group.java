package com.epam.eps.framework.core;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Group {

	private Set<Cell> cells;

	public Group() {
	}

	public Group(Cell... cells) {
		this.cells = Arrays.stream(cells).collect(Collectors.toSet());
	}

	public Set<Cell> getCells() {
		return cells;
	}

	public void setCells(Set<Cell> cells) {
		this.cells = cells;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("new Group(");
		for (Cell cell : cells) {
			string.append(cell).append(", ");
		}
		string.deleteCharAt(string.lastIndexOf(","));
		string.append("),");
		return string.toString();
	}
}
