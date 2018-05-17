package com.epam.eps.model;

import com.epam.eps.framework.core.Cell;

public interface Report {
	String getReport();

	String getSector(Cell[][] quadrantsMatrix);
}
