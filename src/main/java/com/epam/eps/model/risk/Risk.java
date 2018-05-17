package com.epam.eps.model.risk;

import com.epam.eps.framework.core.Group;

import java.util.List;

public interface Risk {
	List<Group> getGroups();

	int getMin();

	int getMax();
}
